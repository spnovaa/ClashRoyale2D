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
     * @param cost    the cost
     * @param radious the radious
     */
    public Spells(String title, int cost, double radious, String thumbImage, String relatedUser) {
        super(title, cost, thumbImage, relatedUser);
        this.radious = radious;
        System.out.println("Radius " + radious + "Set For " + title);
        isAlive = true;
        time=45;
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

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }

    public void setRelatedUser(String relatedUser) {
        this.relatedUser = relatedUser;
    }
    public void  decreaseTime(){
        time--;
    }

    public int getTime() {
        return time;
    }
}
