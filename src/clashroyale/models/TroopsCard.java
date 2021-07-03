package clashroyale.models;

public class TroopsCard extends Card{
    private int hp;
    private int damage;
    private Range rangeType;
    private double hitSpeed;
    private Speed speed;
    private Target target;
    private boolean isAreaSplash;
    private int count;

    public TroopsCard(int cost,int count,double hitSpeed,Target target,Range rangeType,Speed speed,boolean isAreaSplash) {
        super(cost);
        this.count = count;
        this.hitSpeed = hitSpeed;
        this.rangeType=rangeType;
        this.isAreaSplash=isAreaSplash;
        this.speed=speed;
        this.target=target;
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

}
