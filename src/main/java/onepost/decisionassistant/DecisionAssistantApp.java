package onepost.decisionassistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Decision Assistant.
 * Initializes and runs the Spring Boot application.
 */
@SpringBootApplication
public class DecisionAssistantApp {
    
    /**
     * Entry point of the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(DecisionAssistantApp.class, args);
    }
}
