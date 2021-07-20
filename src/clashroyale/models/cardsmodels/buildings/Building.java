package clashroyale.models.cardsmodels.buildings;

import clashroyale.models.enums.Target;
import clashroyale.models.cardsmodels.troops.Card;

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
     * @param cost the cost
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

    public double getHp() {
        return hp;
    }

    public double getDamage() {
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

    public String getRelatedUser() {
        return relatedUser;
    }

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
