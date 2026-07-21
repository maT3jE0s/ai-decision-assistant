package onepost.decisionassistant.model;

/**
 * Represents a single recommendation generated for a user.
 */
public record Recommendation(
	Message message,
	int priorityScore,
	String reason
) {}

