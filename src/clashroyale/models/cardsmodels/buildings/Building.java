package clashroyale.models.cardsmodels.buildings;

import clashroyale.models.enums.Target;
import clashroyale.models.cardsmodels.troops.Card;

public class Building extends Card {
    private int hp;
    private int damage;
    private double hitSpeed;
    private Target target;
    private double range;
    private int lifeTime;
    private float positionX;
    private float positionY;

    /**
     * Instantiates a new Card.
     *
     * @param cost the cost
     */
    public Building(String title, int cost, double range, int lifeTime, double hitSpeed, Target target, String thumbImage) {
        super(title, cost, thumbImage);
        this.hitSpeed = hitSpeed;
        this.range = range;
        this.target=target;
        this.lifeTime=lifeTime;
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

    public double getHitSpeed() {
        return hitSpeed;
    }

    public void setHitSpeed(double hitSpeed) {
        this.hitSpeed = hitSpeed;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
