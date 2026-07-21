package onepost.decisionassistant.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import onepost.decisionassistant.model.Message;

/**
 * Repository providing access to user messages.
 * Currently returns a static list of unresolved messages for demonstration purposes.
 */
@Repository
public class MessageRepository {

	/**
     * Returns unresolved messages for the given user.
     * This implementation provides mock data instead of querying a real data source.
     *
     * @param userId ID of the user
     * @return list of unresolved messages
     */
    public List<Message> findUnresolvedMessagesForUser(Long userId) {
        LocalDateTime now = LocalDateTime.now();

        Message m1 = new Message(
			"1111111111",
			"exekuce@urad.cz",
			"matej",
			"Nová exekuce - nutné řešení",
			"Dostali jsme nový spis, prosíme o okamžitou reakci.",
			now.minusHours(2),
			null
        );

        Message m2 = new Message(
			"2222222222",
			"faktury@firma.cz",
			"matej",
			"Kontrola faktury",
			"Prosíme o schválení faktury do 3 dnů.",
			now.minusDays(1),
			null
        );

        Message m3 = new Message(
			"3333333333",
			"info@urad.cz",
			"matej",
			"Obecná korespondence",
			"Informace o změně provozní doby.",
			now.minusHours(5),
			null
        );

        Message m4 = new Message(
			"4444444444",
			"soud@justice.cz",
			"matej",
			"Soudní jednání - termín",
			"Termín soudního jednání se blíží.",
			now.minusDays(2),
			null
        );

        Message m5 = new Message(
			"5555555555",
			"noreply@bank.cz",
			"matej",
			"Výpis z účtu",
			"Zasíláme měsíční výpis.",
			now.minusDays(3),
			null
        );

        return List.of(m1, m2, m3, m4, m5);
    }
}
