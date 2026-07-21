package onepost.decisionassistant.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import onepost.decisionassistant.model.RecommendationResult;
import onepost.decisionassistant.service.DecisionAssistantService;

/**
 * REST controller providing endpoints for decision recommendations.
 * Handles requests related to retrieving recommendation results for users.
 */
@RestController
@RequestMapping("api/decision-assistant")
public class DecisionAssistantController {
    
    private final DecisionAssistantService decisionAssistantService;

    /**
     * Creates a new instance of the controller with the required service.
     *
     * @param decisionAssistantService service providing recommendation logic
     */
    public DecisionAssistantController(DecisionAssistantService decisionAssistantService) {
        this.decisionAssistantService = decisionAssistantService;
    }

    /**
     * Returns recommendation results for a specific user.
     *
     * @param userId ID of the user
     * @return recommendation result for the given user
     */
    @GetMapping("{userId}/recommendations")
    public RecommendationResult getRecommendations(@PathVariable Long userId) {
        return decisionAssistantService.getRecommendationsForUser(userId);
    }
}
