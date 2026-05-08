package clashroyale.models.systems;

import clashroyale.constants.GameConstants;
import clashroyale.models.UserModel;
import clashroyale.models.game.Robot;

/**
 * Regenerates one elixir for both the player and the bot every
 * {@link GameConstants#ELIXIR_REGEN_FRAMES} frames, up to
 * {@link GameConstants#ELIXIR_MAX}.
 */
public class ElixirSystem {

    private final UserModel userModel;
    private final Robot     bot;
    private int frameCount;

    public ElixirSystem(UserModel userModel, Robot bot) {
        this.userModel  = userModel;
        this.bot        = bot;
        this.frameCount = 0;
    }

    /** Call once per game tick. Increments elixir when the regen threshold is reached. */
    public void tick() {
        frameCount++;
        if (frameCount % GameConstants.ELIXIR_REGEN_FRAMES == 0) {
            incrementElixir(userModel.getElixirCount(), userModel::setElixirCount);
            incrementElixir(bot.getElixirCount(),       bot::setElixirCount);
        }
    }

    private void incrementElixir(int current, java.util.function.IntConsumer setter) {
        if (current < GameConstants.ELIXIR_MAX) setter.accept(current + 1);
    }
}
