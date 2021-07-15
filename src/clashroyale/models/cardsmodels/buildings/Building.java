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

    /**
     * Instantiates a new Card.
     *
     * @param cost the cost
     */
    public Building(String title,int cost,double range,int lifeTime,double hitSpeed,Target target,String thumbImage) {
        super(title,cost,thumbImage);
        this.hitSpeed = hitSpeed;
        this.range=range;
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
}
