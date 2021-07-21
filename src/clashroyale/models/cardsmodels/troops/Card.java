package clashroyale.models.cardsmodels.troops;

import java.util.UUID;

/**
 * The type Card.
 */
public class Card {
    private String title;
    private int cost;
    private String thumbImage;
    private float centerPositionX;
    private float centerPositionY;
    private float radius;
    private String uuid;
    private boolean isAlive;
    private String relatedUser;
    private boolean isBeingBlocked;
    private boolean isAttacking;

    /**
     * Instantiates a new Card.
     *
     * @param cost       the cost
     * @param thumbImage the thumb image
     */
    public Card(String title, int cost, String thumbImage, String relatedUser) {
        this.title = title;
        this.cost = cost;
        this.thumbImage = thumbImage;
        isAlive = true;
        uuid = UUID.randomUUID().toString();
        this.relatedUser = relatedUser;
        isBeingBlocked = false;
        isAttacking = false;
    }

    /**
     * Gets thumb image.
     *
     * @return the thumb image
     */
    public String getThumbImage() {
        return thumbImage;
    }

    /**
     * Gets cost.
     *
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public float getCenterPositionX() {
        return centerPositionX;
    }

    public void setCenterPositionX(float centerPositionX) {
        this.centerPositionX = centerPositionX;
    }

    public float getCenterPositionY() {
        return centerPositionY;
    }

    public void setCenterPositionY(float centerPositionY) {
        this.centerPositionY = centerPositionY;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public String getRelatedUser() {
        return relatedUser;
    }

    public void setRelatedUser(String relatedUser) {
        this.relatedUser = relatedUser;
    }

    public boolean isBeingBlocked() {
        return isBeingBlocked;
    }

    public void setBeingBlocked(boolean beingBlocked) {
        isBeingBlocked = beingBlocked;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }
}
