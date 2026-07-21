package onepost.decisionassistant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import onepost.decisionassistant.model.Message;
import onepost.decisionassistant.model.RecommendationResult;
import onepost.decisionassistant.repository.MessageRepository;

/**
 * Service coordinating the retrieval of user messages and the generation
 * of AI‑based recommendations.
 */
@Service
public class DecisionAssistantService {
    
    private final MessageRepository messageRepository;
    private final AiClient aiClient;

    /**
     * Creates a new instance of the decision assistant service.
     *
     * @param messageRepository repository providing user messages
     * @param aiClient AI client responsible for generating recommendations
     */
    public DecisionAssistantService(MessageRepository messageRepository, AiClient aiClient) {
        this.messageRepository = messageRepository;
        this.aiClient = aiClient;
    }

    /**
     * Retrieves unresolved messages for the given user and delegates
     * recommendation generation to the AI client.
     *
     * @param userId ID of the user
     * @return recommendation result containing prioritized messages and summary
     */
    public RecommendationResult getRecommendationsForUser(Long userId) {
        List<Message> messages = messageRepository.findUnresolvedMessagesForUser(userId);
        return aiClient.getRecommendations(messages);
    }
}
