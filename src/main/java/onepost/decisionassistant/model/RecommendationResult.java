package onepost.decisionassistant.model;

import java.util.List;

/**
 * Represents the final output of the recommendation process.
 */
public record RecommendationResult(
	List<Recommendation> recommendations,
	String overallSummary
) {}
