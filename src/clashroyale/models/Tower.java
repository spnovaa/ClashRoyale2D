package clashroyale.models;

/**
 * The type Tower.
 */
public class Tower {
    private int hp;
    private int damage;
    private double range;
    private double hitSpeed;

    /**
     * Instantiates a new Tower.
     *
     * @param range    the range
     * @param hitSpeed the hit speed
     */
    public Tower(double range,double hitSpeed) {
        this.range = range;
        this.hitSpeed = hitSpeed;
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

    /**
     * Gets hp.
     *
     * @return the hp
     */
    public int getHp() {
        return hp;
    }

    /**
     * Gets damage.
     *
     * @return the damage
     */
    public int getDamage() {
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
}
