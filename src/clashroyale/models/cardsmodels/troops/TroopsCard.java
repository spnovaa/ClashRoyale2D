package clashroyale.models.cardsmodels.troops;

import clashroyale.models.enums.Range;
import clashroyale.models.enums.Speed;
import clashroyale.models.enums.Target;

/**
 * The type Troops card.
 */
public class TroopsCard extends Card {
    private double hp;
    private Double damage;
    private Range rangeType;
    private double hitSpeed;
    private Speed speed;
    private Target target;
    private boolean isAreaSplash;
    private int count;
    private String relatedUser;

//    private float positionX;
//    private float positionY;

    /**
     * Instantiates a new Troops card.
     *
     * @param title        the title
     * @param cost         the cost
     * @param count        the count
     * @param hitSpeed     the hit speed
     * @param target       the target
     * @param rangeType    the range type
     * @param speed        the speed
     * @param isAreaSplash the is area splash
     * @param thumbImage   the thumb image
     * @param relatedUser  the related user
     */
    public TroopsCard(String title, int cost, int count, double hitSpeed, Target target, Range rangeType,
                      Speed speed, boolean isAreaSplash, String thumbImage, String relatedUser) {
        super(title, cost, thumbImage, relatedUser);
        this.count = count;
        this.hitSpeed = hitSpeed;
        this.rangeType = rangeType;
        this.isAreaSplash = isAreaSplash;
        this.speed = speed;
        this.target = target;
        this.relatedUser = relatedUser;
        damage=0.0;
        hp=0;

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
        this.damage = damage * hitSpeed / 15;
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
     * Gets range type.
     *
     * @return the range type
     */
    public Range getRangeType() {
        return rangeType;
    }

    /**
     * Sets range type.
     *
     * @param rangeType the range type
     */
    public void setRangeType(Range rangeType) {
        this.rangeType = rangeType;
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
     * Gets speed.
     *
     * @return the speed
     */
    public Speed getSpeed() {
        return speed;
    }

    /**
     * Sets speed.
     *
     * @param speed the speed
     */
    public void setSpeed(Speed speed) {
        this.speed = speed;
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
     * Is area splash boolean.
     *
     * @return the boolean
     */
    public boolean isAreaSplash() {
        return isAreaSplash;
    }

    /**
     * Sets area splash.
     *
     * @param areaSplash the area splash
     */
    public void setAreaSplash(boolean areaSplash) {
        isAreaSplash = areaSplash;
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets count.
     *
     * @param count the count
     */
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String getRelatedUser() {
        return relatedUser;
    }

    @Override
    public void setRelatedUser(String relatedUser) {
        this.relatedUser = relatedUser;
    }
    //
//    public float getPositionX() {
//        return positionX;
//    }
//
//    public void setPositionX(float positionX) {
//        this.positionX = positionX;
//    }
//
//    public float getPositionY() {
//        return positionY;
//    }
//
//    public void setPositionY(float positionY) {
//        this.positionY = positionY;
//    }
}
