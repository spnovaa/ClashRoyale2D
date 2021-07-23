package clashroyale.models.cardsmodels.spells;

import clashroyale.models.cardsmodels.troops.Card;

/**
 * The type Spells.
 */
public class Spells extends Card {
    private double radious;
    private float positionX;
    private float positionY;
    private Boolean isAlive;
    private int time;
    private String relatedUser;

    /**
     * Instantiates a new Card.
     *
     * @param title       the title
     * @param cost        the cost
     * @param radious     the radious
     * @param thumbImage  the thumb image
     * @param relatedUser the related user
     */
    public Spells(String title, int cost, double radious, String thumbImage, String relatedUser) {
        super(title, cost, thumbImage, relatedUser);
        this.radious = radious * 1.75;
        System.out.println("Radius " + radious + "Set For " + title);
        isAlive = true;
        time = 45;
    }

    /**
     * Gets radious.
     *
     * @return the radious
     */
    public double getRadious() {
        return radious;
    }

    /**
     * Sets radious.
     *
     * @param radious the radious
     */
    public void setRadious(double radious) {
        this.radious = radious;
    }

    /**
     * Gets position x.
     *
     * @return the position x
     */
    public float getPositionX() {
        return positionX;
    }

    /**
     * Sets position x.
     *
     * @param positionX the position x
     */
    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    /**
     * Gets position y.
     *
     * @return the position y
     */
    public float getPositionY() {
        return positionY;
    }

    /**
     * Sets position y.
     *
     * @param positionY the position y
     */
    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    /**
     * Gets alive.
     *
     * @return the alive
     */
    public Boolean getAlive() {
        return isAlive;
    }

    /**
     * Sets alive.
     *
     * @param alive the alive
     */
    public void setAlive(Boolean alive) {
        isAlive = alive;
    }

    public void setRelatedUser(String relatedUser) {
        this.relatedUser = relatedUser;
    }

    /**
     * Decrease time.
     */
    public void decreaseTime() {
        time--;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public int getTime() {
        return time;
    }
}
