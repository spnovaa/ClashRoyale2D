package clashroyale.bots.strategies;

import clashroyale.bots.CardSelectionStrategy;
import clashroyale.models.cardsmodels.troops.Card;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Selection strategy that picks a random card from a fixed hand,
 * provided the bot can afford it.
 *
 * <p>Mirrors the behaviour of the legacy {@code SmartRobot.chooseCardToPlay()}
 * but with an injectable {@link Random} for deterministic tests.</p>
 */
public final class RandomFromHandSelectionStrategy implements CardSelectionStrategy {

    private final List<Card> hand;
    private final Random random;

    public RandomFromHandSelectionStrategy(List<Card> hand) {
        this(hand, new Random());
    }

    public RandomFromHandSelectionStrategy(List<Card> hand, Random random) {
        this.hand   = Objects.requireNonNull(hand, "hand");
        this.random = Objects.requireNonNull(random, "random");
    }

    @Override
    public Card chooseCard(int availableElixir) {
        if (hand.isEmpty()) return null;
        Card candidate = hand.get(random.nextInt(hand.size()));
        return (candidate.getCost() < availableElixir) ? candidate : null;
    }
}
