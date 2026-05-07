package clashroyale.bots;

import clashroyale.models.cardsmodels.troops.Card;
import javafx.geometry.Point2D;

/**
 * Strategy for choosing where on the arena a chosen card will be dropped.
 */
@FunctionalInterface
public interface DeploymentStrategy {

    /**
     * @param card    the card being deployed
     * @param context read-only view of the current arena state
     */
    Point2D chooseLocation(Card card, BotContext context);
}
