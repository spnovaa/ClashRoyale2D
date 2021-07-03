package clashroyale.models;

public class Giant extends TroopsCard {
    public Giant(int level) {
        super(5, 1, 1.5, Target.BUILDINGS, Range.MELEE, Speed.SLOW, false);
        if (level == 1) {
            super.setDamage(126);
            super.setHp(2000);
        } else if (level == 2) {
            super.setDamage(138);
            super.setHp(2200);
        } else if (level == 3) {
            super.setDamage(152);
            super.setHp(2420);
        } else if (level == 4) {
            super.setDamage(167);
            super.setHp(2660);
        } else if (level == 5) {
            super.setDamage(183);
            super.setHp(2920);
        }
    }
}