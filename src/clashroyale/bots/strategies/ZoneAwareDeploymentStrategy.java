package clashroyale.bots.strategies;

import clashroyale.bots.BotContext;
import clashroyale.bots.DeploymentStrategy;
import clashroyale.constants.ArenaConstants;
import clashroyale.models.cardsmodels.spells.Rage;
import clashroyale.models.cardsmodels.troops.Card;
import clashroyale.models.cardsmodels.troops.TroopsCard;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Decision-driven deployment strategy.
 *
 * <p>The arena is divided into four upper quadrants (the bot's reachable
 * half) and the strategy drops the card inside whichever quadrant currently
 * contains the most enemy troops — funnelling defence to where it is
 * needed.</p>
 *
 * <p>{@code Rage} has dedicated handling: it is dropped on top of a
 * friendly troop, falling back to a random quadrant when the bot has no
 * troops on the field yet.</p>
 *
 * <p>This is functionally equivalent to the original {@code SmartRobot}
 * but driven by a clean data-flow:</p>
 *
 * <pre>
 *   build quadrants → count enemies per quadrant → pick max → randomise inside it
 * </pre>
 */
public final class ZoneAwareDeploymentStrategy implements DeploymentStrategy {

    private static final int QUADRANTS = 4;

    private final Random random;
    private final Quadrant[] quadrants;

    public ZoneAwareDeploymentStrategy() {
        this(new Random());
    }

    public ZoneAwareDeploymentStrategy(Random random) {
        this.random    = Objects.requireNonNull(random, "random");
        this.quadrants = buildQuadrants();
    }

    @Override
    public Point2D chooseLocation(Card card, BotContext context) {
        if (card instanceof Rage) {
            return locateNextToFriendlyTroop(context).orElse(randomInsideRandomQuadrant());
        }
        Quadrant target = quadrantWithMostEnemies(context);
        return randomInside(target);
    }

    // ─── Helpers ────────────────────────────────────────────────────────────

    private static Quadrant[] buildQuadrants() {
        // bot's half is split 2 columns × 2 rows.
        int leftX   = ArenaConstants.BOT_FIELD_MIN_X;
        int midX    = ArenaConstants.ARENA_HORIZONTAL_MIDPOINT;
        int rightX  = ArenaConstants.BOT_FIELD_MAX_X;
        int topY    = ArenaConstants.BOT_FIELD_MIN_Y;
        int midY    = ArenaConstants.QUADRANT_TOP_DIVIDER_Y;
        int botY    = ArenaConstants.BOT_FIELD_MAX_Y;
        return new Quadrant[] {
                new Quadrant(leftX,  midX,  topY, midY),
                new Quadrant(midX,   rightX, topY, midY),
                new Quadrant(leftX,  midX,  midY, botY),
                new Quadrant(midX,   rightX, midY, botY),
        };
    }

    private Quadrant quadrantWithMostEnemies(BotContext context) {
        int[] counts = new int[QUADRANTS];
        for (TroopsCard troop : context.arenaTroops()) {
            if (!context.isEnemy(troop)) continue;
            for (int i = 0; i < QUADRANTS; i++) {
                if (quadrants[i].contains(troop.getCenterPositionX(), troop.getCenterPositionY())) {
                    counts[i]++;
                    break;
                }
            }
        }
        int bestIndex = 0;
        for (int i = 1; i < QUADRANTS; i++) {
            if (counts[i] > counts[bestIndex]) bestIndex = i;
        }
        return quadrants[bestIndex];
    }

    private java.util.Optional<Point2D> locateNextToFriendlyTroop(BotContext context) {
        List<TroopsCard> friendlies = new ArrayList<>();
        for (TroopsCard t : context.arenaTroops()) {
            if (context.isFriendly(t)) friendlies.add(t);
        }
        if (friendlies.isEmpty()) return java.util.Optional.empty();
        TroopsCard pick = friendlies.stream()
                .max(Comparator.comparingDouble(TroopsCard::getCenterPositionY))
                .orElse(friendlies.get(0));
        return java.util.Optional.of(new Point2D(pick.getCenterPositionX() + 1,
                                                 pick.getCenterPositionY() + 1));
    }

    private Point2D randomInsideRandomQuadrant() {
        return randomInside(quadrants[random.nextInt(QUADRANTS)]);
    }

    private Point2D randomInside(Quadrant q) {
        float x = randomBetween(q.minX, q.maxX);
        float y = randomBetween(q.minY, q.maxY);
        return new Point2D(x, y);
    }

    private float randomBetween(float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }

    /** Inclusive-of-min, exclusive-of-max rectangle. */
    private record Quadrant(int minX, int maxX, int minY, int maxY) {
        boolean contains(double x, double y) {
            return x >= minX && x < maxX && y >= minY && y < maxY;
        }
    }
}
