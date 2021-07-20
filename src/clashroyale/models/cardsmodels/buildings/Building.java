package clashroyale.models.cardsmodels.buildings;

import clashroyale.models.enums.Target;
import clashroyale.models.cardsmodels.troops.Card;

/**
 * The type Building.
 */
public class Building extends Card {
    private double hp;
    private double damage;
    private double hitSpeed;
    private Target target;
    private double range;
    private int lifeTime;
    private final int fixLifeTime;
    private boolean isAlive;
    private float positionX;
    private float positionY;
    private String relatedUser;

    /**
     * Instantiates a new Card.
     *
     * @param title       the title
     * @param cost        the cost
     * @param range       the range
     * @param lifeTime    the life time
     * @param hitSpeed    the hit speed
     * @param target      the target
     * @param thumbImage  the thumb image
     * @param relatedUser the related user
     */
    public Building(String title, int cost, double range, int lifeTime, double hitSpeed, Target target,
                    String thumbImage, String relatedUser) {
        super(title, cost, thumbImage, relatedUser);
        this.hitSpeed = hitSpeed;
        this.range = range;
        this.target = target;
        this.lifeTime = lifeTime;
        isAlive=true;
        fixLifeTime=lifeTime;
    }

    /**
     * Decrease life time.
     */
    public void decreaseLifeTime(){
        lifeTime--;
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
        this.damage = damage;
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
     * Gets hit speed.
     *
     * @return the hit speed
     */
    public double getHitSpeed() {
        return hitSpeed;
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
     * Gets target.
     *
     * @return the target
     */
    public Target getTarget() {
        return target;
    }

    /**
     * Sets target.
     *
     * @param target the target
     */
    public void setTarget(Target target) {
        this.target = target;
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
     * Sets range.
     *
     * @param range the range
     */
    public void setRange(double range) {
        this.range = range;
    }

    /**
     * Gets life time.
     *
     * @return the life time
     */
    public int getLifeTime() {
        return lifeTime;
    }

    /**
     * Sets life time.
     *
     * @param lifeTime the life time
     */
    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
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
    public void setPositionX(int positionX) {
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
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public String getRelatedUser() {
        return relatedUser;
    }

    /**
     * Gets fix life time.
     *
     * @return the fix life time
     */
    public int getFixLifeTime() {
        return fixLifeTime;
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    public void setRelatedUser(String relatedUser) {
        this.relatedUser = relatedUser;
    }
}
