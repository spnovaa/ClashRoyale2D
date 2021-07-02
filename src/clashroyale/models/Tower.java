package clashroyale.models;

public class Tower {
    private int hp;
    private int damage;
    private double range;
    private double hitSpeed;

    public Tower(double range,double hitSpeed) {
        this.range = range;
        this.hitSpeed = hitSpeed;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
