package clashroyale.models.game;

import clashroyale.models.cardsmodels.troops.Card;
import javafx.geometry.Point2D;

import java.util.Random;


/**
 * The type Simple robot.
 */
public class SimpleRobot extends Robot {
    /**
     * Instantiates a new Simple robot.
     *
     * @param level the level
     */
    public SimpleRobot(int level) {
        super("simpleBot", level);
    }

    /**
     * Choose card to play card.
     *
     * @return the card
     */
    public Card chooseCardToPlay() {

        Card card = super.getCardsQue().peek();
        if (card.getCost() < getElixirCount()) {
            super.getCardsQue().poll();
            super.getCardsQue().add(card);
            return card;
        } else return null;
    }

    /**
     * Choose coordinates to play point 2 d.
     *
     * @return the point 2 d
     */
    public synchronized Point2D chooseCoordinatesToPlay() {
        float deployedX = getRandomNumberUsingNextInt(super.getMinX(), super.getMaxX());
        float deployedY = getRandomNumberUsingNextInt(super.getMinY(), super.getMaxY());
//        System.out.println( deployedX+ " : "+ deployedY);
        return new Point2D(deployedX, deployedY);
    }

    /**
     * Gets random number using next int.
     *
     * @param min the min
     * @param max the max
     * @return the random number using next int
     */
    public float getRandomNumberUsingNextInt(float min, float max) {
        Random random = new Random();
        return random.nextFloat() * (max - min) + min;
    }
}
