package clashroyale.bots;

import clashroyale.bots.strategies.QueueCycleSelectionStrategy;
import clashroyale.bots.strategies.RandomDeploymentStrategy;
import clashroyale.bots.strategies.RandomFromHandSelectionStrategy;
import clashroyale.bots.strategies.ZoneAwareDeploymentStrategy;
import clashroyale.constants.GameConstants;
import clashroyale.models.cardsmodels.buildings.Building;
import clashroyale.models.cardsmodels.spells.Spells;
import clashroyale.models.cardsmodels.troops.Card;
import clashroyale.models.cardsmodels.troops.TroopsCard;
import clashroyale.models.enums.BotKind;
import clashroyale.models.factory.CardFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Assembles {@link BotPlayer} instances of the requested {@link BotKind},
 * pre-loading them with a level-appropriate deck and the strategy combo
 * that matches the chosen difficulty.
 *
 * <p>This is the only place that knows which strategies pair to make a
 * "simple" or "smart" bot — game code stays decoupled from those details.</p>
 */
public final class BotPlayerFactory {

    private BotPlayerFactory() {}

    public static BotPlayer create(BotKind kind, int level) {
        return switch (kind) {
            case SIMPLE -> simple(level);
            case SMART  -> smart(level);
        };
    }

    private static BotPlayer simple(int level) {
        List<Card> shuffled = shuffledFullDeck(level, BotKind.SIMPLE.legacyId());
        Queue<Card> deck = new LinkedList<>(shuffled.subList(0, GameConstants.DECK_SIZE));
        return new BotPlayer(
                BotKind.SIMPLE,
                level,
                new QueueCycleSelectionStrategy(deck),
                new RandomDeploymentStrategy()
        );
    }

    private static BotPlayer smart(int level) {
        List<Card> hand = composeBalancedHand(level, BotKind.SMART.legacyId());
        return new BotPlayer(
                BotKind.SMART,
                level,
                new RandomFromHandSelectionStrategy(hand),
                new ZoneAwareDeploymentStrategy()
        );
    }

    private static List<Card> shuffledFullDeck(int level, String relatedUser) {
        List<Card> all = new ArrayList<>(CardFactory.createAll(level, relatedUser));
        Collections.shuffle(all);
        return all;
    }

    /** Roughly: {@code SMART_BOT_TROOP_SLOTS} troops, {@code SPELL_SLOTS} spells, {@code BUILDING_SLOTS} buildings. */
    private static List<Card> composeBalancedHand(int level, String relatedUser) {
        List<Card> all = shuffledFullDeck(level, relatedUser);
        List<Card> troops    = pick(all, c -> c instanceof TroopsCard, GameConstants.SMART_BOT_TROOP_SLOTS);
        List<Card> spells    = pick(all, c -> c instanceof Spells,     GameConstants.SMART_BOT_SPELL_SLOTS);
        List<Card> buildings = pick(all, c -> c instanceof Building,   GameConstants.SMART_BOT_BUILDING_SLOTS);

        List<Card> hand = new ArrayList<>(GameConstants.DECK_SIZE);
        hand.addAll(troops);
        hand.addAll(spells);
        hand.addAll(buildings);
        Collections.shuffle(hand);
        return hand;
    }

    private static List<Card> pick(List<Card> source, java.util.function.Predicate<Card> filter, int max) {
        List<Card> picked = new ArrayList<>(max);
        for (Card c : source) {
            if (filter.test(c)) {
                picked.add(c);
                if (picked.size() == max) break;
            }
        }
        return picked;
    }
}
