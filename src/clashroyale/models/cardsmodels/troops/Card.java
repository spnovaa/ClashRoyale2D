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
     * @param title       the title
     * @param cost        the cost
     * @param thumbImage  the thumb image
     * @param relatedUser the related user
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

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets cost.
     *
     * @param cost the cost
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Sets thumb image.
     *
     * @param thumbImage the thumb image
     */
    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    /**
     * Gets center position x.
     *
     * @return the center position x
     */
    public float getCenterPositionX() {
        return centerPositionX;
    }

    /**
     * Sets center position x.
     *
     * @param centerPositionX the center position x
     */
    public void setCenterPositionX(float centerPositionX) {
        this.centerPositionX = centerPositionX;
    }

    /**
     * Gets center position y.
     *
     * @return the center position y
     */
    public float getCenterPositionY() {
        return centerPositionY;
    }

    /**
     * Sets center position y.
     *
     * @param centerPositionY the center position y
     */
    public void setCenterPositionY(float centerPositionY) {
        this.centerPositionY = centerPositionY;
    }

    /**
     * Gets radius.
     *
     * @return the radius
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Sets radius.
     *
     * @param radius the radius
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }

    /**
     * Gets uuid.
     *
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets uuid.
     *
     * @param uuid the uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Is alive boolean.
     *
     * @return the boolean
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Sets alive.
     *
     * @param alive the alive
     */
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    /**
     * Gets related user.
     *
     * @return the related user
     */
    public String getRelatedUser() {
        return relatedUser;
    }

    /**
     * Sets related user.
     *
     * @param relatedUser the related user
     */
    public void setRelatedUser(String relatedUser) {
        this.relatedUser = relatedUser;
    }

    /**
     * Is being blocked boolean.
     *
     * @return the boolean
     */
    public boolean isBeingBlocked() {
        return isBeingBlocked;
    }

    /**
     * Sets being blocked.
     *
     * @param beingBlocked the being blocked
     */
    public void setBeingBlocked(boolean beingBlocked) {
        isBeingBlocked = beingBlocked;
    }

    /**
     * Is attacking boolean.
     *
     * @return the boolean
     */
    public boolean isAttacking() {
        return isAttacking;
    }

    /**
     * Sets attacking.
     *
     * @param attacking the attacking
     */
    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }
}
