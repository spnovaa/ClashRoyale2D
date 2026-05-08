package clashroyale.models.game;

import clashroyale.models.GameModel;
import clashroyale.models.cardsmodels.buildings.Building;
import clashroyale.models.cardsmodels.spells.Rage;
import clashroyale.models.cardsmodels.spells.Spells;
import clashroyale.models.cardsmodels.troops.Card;
import clashroyale.models.cardsmodels.troops.TroopsCard;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * AI opponent that deploys cards into whichever arena zone currently holds the
 * most enemy troops.
 *
 * <p>Zone layout (top = bot half, bottom = player half):
 * <pre>
 *  Z1 (left,  top)   Z2 (right, top)
 *  Z3 (left,  mid)   Z4 (right, mid)
 *  [bridge zone — not a deployment target]
 *  Z5 (left,  low)   Z6 (right, low)
 *  Z7 (left,  bot)   Z8 (right, bot)
 * </pre>
 * Only zones Z1–Z4 are used for enemy-count analysis; Z5–Z8 describe the bot's
 * own half and are computed from bridge coordinates at runtime.
 * </p>
 */
public class SmartRobot extends Robot {

    /**
     * Immutable rectangular zone on the arena.
     *
     * <p>Replacing the eight groups of four {@code int} fields (minX1..maxY8)
     * with a typed record eliminates 32 fields and makes zone iteration trivial.</p>
     */
    private record Zone(int minX, int maxX, int minY, int maxY) {
        boolean contains(float x, float y) {
            return x > minX && x < maxX && y > minY && y < maxY;
        }
        float randomX(Random rng) { return rng.nextFloat() * (maxX - minX) + minX; }
        float randomY(Random rng) { return rng.nextFloat() * (maxY - minY) + minY; }
    }

    // ── Enemy-analysis zones (top half of the arena) ──────────────────────────
    private static final Zone Z1 = new Zone( 25, 183,  35, 133);
    private static final Zone Z2 = new Zone(183, 340,  35, 133);
    private static final Zone Z3 = new Zone( 25, 183, 133, 230);
    private static final Zone Z4 = new Zone(183, 340, 133, 230);
    private static final List<Zone> ANALYSIS_ZONES = List.of(Z1, Z2, Z3, Z4);

    // ── Bot deployment zones (bottom half — computed after setLiveData) ────────
    private Zone[] deployZones;

    private final ArrayList<Card> smartBotCards;
    private GameModel             gameModel;
    private final Random          random = new Random();

    /**
     * Instantiates a new Smart robot.
     *
     * @param level the level
     */
    public SmartRobot(int level) {
        super("smartBot", level);
        smartBotCards = buildDeck();
    }

    // ── Public API ────────────────────────────────────────────────────────────

    /**
     * Supplies the current arena state so the bot can make informed decisions.
     * Must be called before {@link #chooseCardToPlay()} and
     * {@link #chooseCoordinatesToPlay(Card)} each tick.
     */
    public void setLiveData(GameModel gameModel) {
        this.gameModel  = gameModel;
        this.deployZones = buildDeployZones(gameModel);
    }

    /**
     * Randomly selects a card the bot can currently afford.
     *
     * @return a playable card, or {@code null} if none can be afforded
     */
    public Card chooseCardToPlay() {
        if (smartBotCards.isEmpty()) return null;
        Card card = smartBotCards.get(random.nextInt(smartBotCards.size()));
        return card.getCost() < getElixirCount() ? card : null;
    }

    /**
     * Returns deployment coordinates for the given card.
     * Rage spells are placed on top of a friendly troop; other cards are
     * deployed into the zone with the highest enemy presence.
     */
    public Point2D chooseCoordinatesToPlay(Card card) {
        if (card instanceof Rage) return rageTarget();

        Zone best = bestZone();
        return best != null
                ? new Point2D(best.randomX(random), best.randomY(random))
                : new Point2D(deployZones[0].randomX(random), deployZones[0].randomY(random));
    }

    // ── Deck building ─────────────────────────────────────────────────────────

    /**
     * Assembles a deck of 1 building + 5 troops + 2 spells and shuffles it.
     */
    private ArrayList<Card> buildDeck() {
        ArrayList<Card> deck = new ArrayList<>();
        int troops   = 0;
        int spells   = 0;
        int buildings = 0;

        for (Card card : super.getAllCards()) {
            if (card instanceof Building && buildings < 1) {
                deck.add(card); buildings++;
            } else if (card instanceof Spells && spells < 2) {
                deck.add(card); spells++;
            } else if (card instanceof TroopsCard && troops < 5) {
                deck.add(card); troops++;
            }
            if (buildings >= 1 && spells >= 2 && troops >= 5) break;
        }
        Collections.shuffle(deck);
        return deck;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /** Finds deployment coordinates that land on a friendly troop. */
    private Point2D rageTarget() {
        for (TroopsCard troop : gameModel.getArenaExistingTroops()) {
            if ("smartBot".equals(troop.getRelatedUser())) {
                return new Point2D(troop.getCenterPositionX() + 1,
                                   troop.getCenterPositionY() + 1);
            }
        }
        // Fallback: centre of first deploy zone
        return new Point2D(deployZones[0].randomX(random), deployZones[0].randomY(random));
    }

    /** Returns the analysis zone with the highest number of enemy troops. */
    private Zone bestZone() {
        int[] counts = new int[ANALYSIS_ZONES.size()];
        for (TroopsCard troop : gameModel.getArenaExistingTroops()) {
            if ("smartBot".equals(troop.getRelatedUser())) continue;
            for (int i = 0; i < ANALYSIS_ZONES.size(); i++) {
                if (ANALYSIS_ZONES.get(i).contains(troop.getCenterPositionX(),
                                                    troop.getCenterPositionY())) {
                    counts[i]++;
                }
            }
        }
        int maxIdx = 0;
        for (int i = 1; i < counts.length; i++) {
            if (counts[i] > counts[maxIdx]) maxIdx = i;
        }
        return ANALYSIS_ZONES.get(maxIdx);
    }

    /**
     * Computes bot deployment zones from the current bridge coordinates.
     * The bot may only deploy in its own half (below the bridge).
     */
    private static Zone[] buildDeployZones(GameModel gm) {
        int bridgeStart = (int) gm.getBridgesStartY();
        return new Zone[]{
            new Zone( 25, 183, bridgeStart,       340),
            new Zone(183, 340, bridgeStart,       340),
            new Zone( 25, 183,          340,      450),
            new Zone(183, 340,          340,      450),
        };
    }
}
