package clashroyale.bots;

import clashroyale.models.cardsmodels.troops.Card;
import clashroyale.models.cardsmodels.troops.TroopsCard;

import java.util.Collections;
import java.util.List;

/**
 * Read-only view of game state that a {@link DeploymentStrategy}
 * needs to decide where to drop a card.
 *
 * <p>By passing a context object instead of a reference to {@code GameModel}
 * we keep strategies testable in isolation — feature tests construct a
 * trivial context with hand-built lists and never touch the engine.</p>
 */
public final class BotContext {

    private final List<TroopsCard> arenaTroops;
    private final String botId;

    public BotContext(List<TroopsCard> arenaTroops, String botId) {
        this.arenaTroops = (arenaTroops == null)
                ? Collections.emptyList()
                : List.copyOf(arenaTroops);
        this.botId = botId;
    }

    public List<TroopsCard> arenaTroops() { return arenaTroops; }

    public String botId() { return botId; }

    public boolean isFriendly(Card card) {
        return card != null && botId != null && botId.equals(card.getRelatedUser());
    }

    public boolean isEnemy(Card card) {
        return card != null && !isFriendly(card);
    }
}
