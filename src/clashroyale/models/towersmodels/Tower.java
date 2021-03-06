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
     * @param range    the range
     * @param hitSpeed the hit speed
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

    public void setRange(double range) {
        this.range = range;
    }

    public void setHitSpeed(double hitSpeed) {
        this.hitSpeed = hitSpeed;
    }

    public String getRelatedUser() {
        return relatedUser;
    }

    public void setRelatedUser(String relatedUser) {
        this.relatedUser = relatedUser;
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

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public Card getTarget() {
        return target;
    }

    public void setTarget(Card target) {
        this.target = target;
    }
}
