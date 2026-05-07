package clashroyale.bots.strategies;

import clashroyale.bots.CardSelectionStrategy;
import clashroyale.models.cardsmodels.troops.Card;

import java.util.Objects;
import java.util.Queue;

/**
 * Selection strategy that always tries the front of a fixed deck queue,
 * cycling the chosen card to the back so the same one will not be played
 * twice in a row.
 *
 * <p>This is the behaviour the original {@code SimpleRobot} relied on.</p>
 */
public final class QueueCycleSelectionStrategy implements CardSelectionStrategy {

    private final Queue<Card> deck;

    public QueueCycleSelectionStrategy(Queue<Card> deck) {
        this.deck = Objects.requireNonNull(deck, "deck");
    }

    @Override
    public Card chooseCard(int availableElixir) {
        Card next = deck.peek();
        if (next == null || next.getCost() >= availableElixir) {
            return null;
        }
        deck.poll();
        deck.add(next);
        return next;
    }
}
