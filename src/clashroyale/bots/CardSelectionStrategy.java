package clashroyale.bots;

import clashroyale.models.cardsmodels.troops.Card;

/**
 * Strategy for choosing which card to deploy next from a bot's deck.
 *
 * <p>Allows new selection heuristics (queue-based, weighted-random,
 * counter-pick, learned policy) to be plugged into {@link BotPlayer}
 * without modifying the bot itself or any of its peers.</p>
 */
@FunctionalInterface
public interface CardSelectionStrategy {

    /**
     * @param availableElixir current elixir budget the bot may spend
     * @return the card to play, or {@code null} when the bot should hold
     */
    Card chooseCard(int availableElixir);
}
