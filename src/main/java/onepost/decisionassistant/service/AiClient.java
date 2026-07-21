package onepost.decisionassistant.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;

import onepost.decisionassistant.model.*;

/**
 * Service responsible for communicating with the Gemini AI model
 * and transforming its output into application-level recommendation results.
 */
@Service
public class AiClient {

    private final Client client;
    private final ObjectMapper objectMapper;
    private final String model;

    /**
     * Creates a new AI client service.
     *
     * @param client Gemini API client
     * @param objectMapper JSON serializer/deserializer
     * @param model model name configured in application configuration
     */
    public AiClient(Client client,
                    ObjectMapper objectMapper,
                    @Value("${gemini.model}") String model) {
        this.client = client;
        this.objectMapper = objectMapper;
        this.model = model;
    }

    /**
     * Internal DTO representing a single AI-generated recommendation.
     */
    private record AiRecommendation(String messageId, int priorityScore, String reason) {
    }

    /**
     * Internal DTO representing the full AI response.
     */
    private record AiRecommendationResponse(List<AiRecommendation> recommendations, String overallSummary) {
    }

    /**
     * Sends user messages to the AI model, receives structured JSON output,
     * validates it, and converts it into domain-level recommendation objects.
     *
     * @param messages list of unresolved user messages
     * @return structured recommendation result containing sorted recommendations and summary
     */
    public RecommendationResult getRecommendations(List<Message> messages) {
        String messagesJson;
        try {
            messagesJson = objectMapper.writeValueAsString(messages);
        } catch (Exception e) {
            throw new RuntimeException("Nepodarilo se serializovat zpravy pro AI", e);
        }

        String prompt = """
                Jsi AI Decision Assistant pro platformu ONEPOST (sprava datovych schranek v CR).

                Ukol:
                - Projdi seznam nevyresenych zprav uzivatele.
                - Urci, ktere zpravy je treba resit prednostne, s ohledem na pravni a financni riziko
                  plynouci z jejich ignorovani (napr. exekuce, soudni terminy, splatnosti faktur).
                - Kazde zprave prirad priorityScore v rozsahu 0-100, kde 100 je nejnalehavejsi.
                - Ke kazde zprave uved kratke a konkretni odduvodneni v cestine.
                - Uved overallSummary - strucne shrnuti aktualni situace uzivatele v 1-2 vetach cesky.

                U kazde polozky v "recommendations" vrat pouze "messageId" odpovidajici poli "id"
                ze vstupnich dat nize - NEVYPISUJ cely obsah zpravy zpet.

                Vráť platný JSON v tomto presnom formáte.

                {
                    "overallSummary": "string",
                    "recommendations": [
                        {
                        "messageId": "string",
                        "priorityScore": 0,
                        "reason": "string"
                        }
                    ]
                }
                    
                Zpravy k analyze (JSON):
                %s
                """.formatted(messagesJson);

        GenerateContentResponse response =
            client.models.generateContent(
                model,
                prompt,
                GenerateContentConfig.builder()
                    .responseMimeType("application/json")
                    .build()
            );

        try {
            AiRecommendationResponse raw = objectMapper.readValue(response.text(), AiRecommendationResponse.class);

            if (raw == null || raw.recommendations() == null) {
                throw new RuntimeException("AI nevratila pouzitelnou odpoved");
            }

            Map<String, Message> byId = messages.stream()
                    .collect(Collectors.toMap(Message::id, m -> m));

            List<Recommendation> sorted = raw.recommendations().stream()
                    .filter(r -> byId.containsKey(r.messageId()))
                    .map(r -> new Recommendation(byId.get(r.messageId()), r.priorityScore(), r.reason()))
                    .sorted(Comparator.comparingInt(Recommendation::priorityScore).reversed())
                    .toList();

            return new RecommendationResult(sorted, raw.overallSummary());
        }
        catch (Exception e) {
            throw new RuntimeException("AI vratila neplatny JSON: " + response, e);
        }
    }
}
