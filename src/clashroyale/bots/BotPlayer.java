package clashroyale.bots;

import clashroyale.constants.GameConstants;
import clashroyale.models.cardsmodels.troops.Card;
import clashroyale.models.enums.BotKind;
import javafx.geometry.Point2D;

import java.util.Objects;

/**
 * A bot opponent built around two pluggable strategies — Strategy pattern.
 *
 * <ul>
 *   <li>{@link CardSelectionStrategy} decides <em>what</em> to play.</li>
 *   <li>{@link DeploymentStrategy} decides <em>where</em> to play it.</li>
 * </ul>
 *
 * <p>This replaces the previous inheritance-driven design ({@code SimpleRobot}
 * and {@code SmartRobot} extending {@code Robot}). New bot personalities
 * become plain combinations of strategies — no further subclassing.</p>
 *
 * <p>The bot is intentionally framework-agnostic and stateless w.r.t. the
 * engine: the caller passes a fresh {@link BotContext} on every decision
 * tick.</p>
 */
public final class BotPlayer {

    private final BotKind kind;
    private final int     level;
    private final CardSelectionStrategy selectionStrategy;
    private final DeploymentStrategy    deploymentStrategy;

    private int elixir;

    public BotPlayer(BotKind kind,
                     int level,
                     CardSelectionStrategy selectionStrategy,
                     DeploymentStrategy deploymentStrategy) {
        this.kind               = Objects.requireNonNull(kind, "kind");
        this.level              = level;
        this.selectionStrategy  = Objects.requireNonNull(selectionStrategy,  "selectionStrategy");
        this.deploymentStrategy = Objects.requireNonNull(deploymentStrategy, "deploymentStrategy");
        this.elixir             = GameConstants.ELIXIR_INITIAL;
    }

    /**
     * Result of a single decision. {@link #card} is {@code null} when
     * the bot decided to hold this tick.
     */
    public record Move(Card card, Point2D location) {
        public boolean isHold() { return card == null; }
    }

    /**
     * Decide on (and return) the next move given the current context.
     * Does not mutate the context — callers are responsible for
     * applying the move to the engine state.
     */
    public Move decide(BotContext context) {
        Card card = selectionStrategy.chooseCard(elixir);
        if (card == null) return new Move(null, null);

        Point2D location = deploymentStrategy.chooseLocation(card, context);
        return new Move(card, location);
    }

    public BotKind kind()           { return kind; }
    public int     level()          { return level; }
    public int     elixir()         { return elixir; }

    public void    addElixir(int delta) {
        elixir = Math.max(0, Math.min(GameConstants.ELIXIR_MAX, elixir + delta));
    }

    public void    spendElixir(int amount) {
        if (amount > elixir) {
            throw new IllegalStateException("Insufficient elixir: have " + elixir + ", need " + amount);
        }
        elixir -= amount;
    }
}
