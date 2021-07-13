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
    public TroopsCard(String title,int cost,int count,double hitSpeed,Target target,Range rangeType,Speed speed,boolean isAreaSplash,String thumbImage) {
        super(title,cost,thumbImage);
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
