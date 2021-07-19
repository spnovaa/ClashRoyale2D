package clashroyale.models.cardsmodels.troops;

import clashroyale.models.enums.Range;
import clashroyale.models.enums.Speed;
import clashroyale.models.enums.Target;

/**
 * The type Troops card.
 */
public class TroopsCard extends Card {
    private int hp;
    private int damage;
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
     * @param cost         the cost
     * @param count        the count
     * @param hitSpeed     the hit speed
     * @param target       the target
     * @param rangeType    the range type
     * @param speed        the speed
     * @param isAreaSplash the is area splash
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
    }

    /**
     * Sets hp.
     *
     * @param hp the hp
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * Sets damage.
     *
     * @param damage the damage
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public Range getRangeType() {
        return rangeType;
    }

    public void setRangeType(Range rangeType) {
        this.rangeType = rangeType;
    }

    public double getHitSpeed() {
        return hitSpeed;
    }

    public void setHitSpeed(double hitSpeed) {
        this.hitSpeed = hitSpeed;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public boolean isAreaSplash() {
        return isAreaSplash;
    }

    public void setAreaSplash(boolean areaSplash) {
        isAreaSplash = areaSplash;
    }

    public int getCount() {
        return count;
    }

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
