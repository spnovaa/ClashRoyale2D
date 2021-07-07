package clashroyale.models;

/**
 * The type Barbarians card.
 */
public class BarbariansCard extends TroopsCard{

    /**
     * Instantiates a new Barbarians card.
     *
     * @param level the level
     */
    public BarbariansCard(int level) {
        super(5,  4, 1.5, Target.GROUND, Range.MELEE, Speed.MEDIUM,false );
        if (level == 1) {
            super.setDamage(75);
            super.setHp(300);
        } else if (level == 2) {
            super.setDamage(82);
            super.setHp(330);
        } else if (level == 3) {
            super.setDamage(90);
            super.setHp(363);
        } else if (level == 4) {
            super.setDamage(99);
            super.setHp(438);
        } else if (level == 5) {
            super.setDamage(102);
            super.setHp(480);
        }
    }
}
