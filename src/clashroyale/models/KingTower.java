package clashroyale.models;

public class KingTower extends Tower {
    public KingTower(int level) {
        super(7, 1);
        if (level == 1) {
            super.setDamage(50);
            super.setHp(2400);
        } else if (level == 2) {
            super.setDamage(53);
            super.setHp(2568);
        } else if (level == 3) {
            super.setDamage(57);
            super.setHp(2736);
        } else if (level == 4) {
            super.setDamage(60);
            super.setHp(2904);
        } else if (level == 5) {
            super.setDamage(64);
            super.setHp(3096);
        }
    }
}
