package clashroyale.models.game;

import clashroyale.models.cardsmodels.troops.Card;
import javafx.geometry.Point2D;

import java.util.Random;

/**
 * The type Simple robot.
 */
public class SimpleRobot extends Robot {
    public SimpleRobot(int level) {
        super("simpleBot", level);
    }

    public Card chooseCardToPlay() {

        Card card = super.getCardsQue().peek();
        if (card.getCost() < getElixirCount()) {
            super.getCardsQue().poll();
            super.getCardsQue().add(card);
            return card;
        } else return null;
    }

    public synchronized Point2D chooseCoordinatesToPlay() {
        float deployedX = getRandomNumberUsingNextInt(super.getMinX(), super.getMaxX());
        float deployedY = getRandomNumberUsingNextInt(super.getMinY(), super.getMaxY());
//        System.out.println( deployedX+ " : "+ deployedY);
        return new Point2D(deployedX, deployedY);
    }

    public float getRandomNumberUsingNextInt(float min, float max) {
        Random random = new Random();
        return random.nextFloat() * (max - min) + min;
    }
}
