package onepost.decisionassistant.model;

import java.time.LocalDateTime;

/**
 * Represents a single message exchanged between users.
 */
public record Message(
	String id,
	String from,
	String to,
	String subject,
	String bodyText,
	LocalDateTime receivedAt,
	LocalDateTime readAt
) {}

