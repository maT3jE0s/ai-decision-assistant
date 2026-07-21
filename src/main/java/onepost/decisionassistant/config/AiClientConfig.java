package onepost.decisionassistant.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.genai.Client;

/**
 * Configuration class for initializing the Gemini AI client.
 * Loads the API key from application properties and exposes a configured client bean.
 */
@Configuration
public class AiClientConfig {

    @Value("${gemini.api-key}")
    private String apiKey;

    /**
     * Creates and configures the Gemini API client.
     *
     * @return a fully initialized Gemini client instance
     */
    @Bean
    public Client geminiClient() {
        return Client.builder()
                .apiKey(apiKey)
                .build();
    }
}
