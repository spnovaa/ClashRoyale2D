package clashroyale.models;

public class ValkyrieCard extends TroopsCard{
    public ValkyrieCard(int level) {
        super(4,  1, 1.5, Target.GROUND, Range.MELEE, Speed.MEDIUM,true );
        if (level == 1) {
            super.setDamage(120);
            super.setHp(880);
        } else if (level == 2) {
            super.setDamage(132);
            super.setHp(968);
        } else if (level == 3) {
            super.setDamage(145);
            super.setHp(1064);
        } else if (level == 4) {
            super.setDamage(159);
            super.setHp(1170);
        } else if (level == 5) {
            super.setDamage(175);
            super.setHp(1248);
        }
    }
}
