package clashroyale.models.towersmodels;

import clashroyale.models.cardsmodels.troops.Card;

import java.util.UUID;

/**
 * The type Tower.
 */
public class Tower {
    private double hp;
    private double damage;
    private double range;
    private double hitSpeed;
    private String relatedUser;
    private float centerPositionX;
    private float centerPositionY;
    private float radius;
    private boolean isAlive;
    private String uuid;
    private String title;
    private boolean isAttacking;
    private Card target;

    /**
     * Instantiates a new Tower.
     *
     * @param range           the range
     * @param hitSpeed        the hit speed
     * @param relatedUser     the related user
     * @param centerPositionX the center position x
     * @param centerPositionY the center position y
     * @param radius          the radius
     * @param title           the title
     */
    public Tower(double range, double hitSpeed, String relatedUser, float centerPositionX, float centerPositionY,
                 float radius, String title) {
        this.range = range;
        this.hitSpeed = hitSpeed;
        this.relatedUser = relatedUser;
        this.centerPositionX = centerPositionX;
        this.centerPositionY = centerPositionY;
        this.radius = radius;
        isAlive = true;
        uuid = UUID.randomUUID().toString();
        this.title = title;
        isAttacking = false;
        target = null;
    }

    /**
     * Sets hp.
     *
     * @param hp the hp
     */
    public void setHp(double hp) {
        this.hp = hp;
    }

    /**
     * Sets damage.
     *
     * @param damage the damage
     */
    public void setDamage(double damage) {
        this.damage = damage / 15;
    }

    /**
     * Gets hp.
     *
     * @return the hp
     */
    public double getHp() {
        return hp;
    }

    /**
     * Gets damage.
     *
     * @return the damage
     */
    public double getDamage() {
        return damage;
    }

    /**
     * Gets range.
     *
     * @return the range
     */
    public double getRange() {
        return range;
    }

    /**
     * Gets hit speed.
     *
     * @return the hit speed
     */
    public double getHitSpeed() {
        return hitSpeed;
    }

    /**
     * Sets range.
     *
     * @param range the range
     */
    public void setRange(double range) {
        this.range = range;
    }

    /**
     * Sets hit speed.
     *
     * @param hitSpeed the hit speed
     */
    public void setHitSpeed(double hitSpeed) {
        this.hitSpeed = hitSpeed;
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

    /**
     * Gets target.
     *
     * @return the target
     */
    public Card getTarget() {
        return target;
    }

    /**
     * Sets target.
     *
     * @param target the target
     */
    public void setTarget(Card target) {
        this.target = target;
    }
}
