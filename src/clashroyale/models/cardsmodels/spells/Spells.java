package clashroyale.models.cardsmodels.spells;

import clashroyale.models.cardsmodels.troops.Card;

/**
 * The type Spells.
 */
public class Spells extends Card {
    private double radious;
    private float positionX;
    private float positionY;

    /**
     * Instantiates a new Card.
     *
     * @param cost    the cost
     * @param radious the radious
     */
    public Spells(String title, int cost, double radious, String thumbImage) {
        super(title, cost, thumbImage);
        this.radious = radious;
    }

    public double getRadious() {
        return radious;
    }

    public void setRadious(double radious) {
        this.radious = radious;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }
}
