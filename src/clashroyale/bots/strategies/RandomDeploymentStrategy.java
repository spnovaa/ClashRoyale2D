package clashroyale.bots.strategies;

import clashroyale.bots.BotContext;
import clashroyale.bots.DeploymentStrategy;
import clashroyale.constants.ArenaConstants;
import clashroyale.models.cardsmodels.troops.Card;
import javafx.geometry.Point2D;

import java.util.Objects;
import java.util.Random;

/**
 * Drops a card at a uniformly random point inside the bot's playable
 * field. This is the behaviour the legacy {@code SimpleRobot} relied on.
 *
 * <p>Field bounds come from {@link ArenaConstants} — no more hardcoded
 * 25 / 340 / 35 / 230 sprinkled in the code.</p>
 */
public final class RandomDeploymentStrategy implements DeploymentStrategy {

    private final Random random;

    public RandomDeploymentStrategy() {
        this(new Random());
    }

    public RandomDeploymentStrategy(Random random) {
        this.random = Objects.requireNonNull(random, "random");
    }

    @Override
    public Point2D chooseLocation(Card card, BotContext context) {
        float x = randomBetween(ArenaConstants.BOT_FIELD_MIN_X, ArenaConstants.BOT_FIELD_MAX_X);
        float y = randomBetween(ArenaConstants.BOT_FIELD_MIN_Y, ArenaConstants.BOT_FIELD_MAX_Y);
        return new Point2D(x, y);
    }

    private float randomBetween(float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }
}
