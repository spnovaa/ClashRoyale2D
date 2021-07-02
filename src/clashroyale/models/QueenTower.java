package clashroyale.models;

public class QueenTower extends Tower{
    public QueenTower(int level) {
        super(7.5, 0.8);
        if (level == 1) {
            super.setDamage(50);
            super.setHp(1400);
        } else if (level == 2) {
            super.setDamage(54);
            super.setHp(1512);
        } else if (level == 3) {
            super.setDamage(58);
            super.setHp(1624);
        } else if (level == 4) {
            super.setDamage(62);
            super.setHp(1750);
        } else if (level == 5) {
            super.setDamage(69);
            super.setHp(1890);
        }
    }
}
