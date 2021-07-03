package clashroyale.models;

public class BabyDragonCard extends TroopsCard {
    private int range;
    public BabyDragonCard(int level) {
        super(4,  1, 1.8, Target.AIR_GROUND, Range.RAMGED, Speed.FAST,true );
        range=3;
        if (level == 1) {
            super.setDamage(100);
            super.setHp(800);
        } else if (level == 2) {
            super.setDamage(110);
            super.setHp(880);
        } else if (level == 3) {
            super.setDamage(121);
            super.setHp(968);
        } else if (level == 4) {
            super.setDamage(133);
            super.setHp(1064);
        } else if (level == 5) {
            super.setDamage(146);
            super.setHp(1168);
        }
    }
}