package clashroyale.models;

/**
 * The type Wizard card.
 */
public class WizardCard extends TroopsCard{
  private   int range;

    /**
     * Instantiates a new Wizard card.
     *
     * @param level the level
     */
    public WizardCard(int level) {
        super(5,  1, 1.7, Target.AIR_GROUND, Range.RAMGED, Speed.MEDIUM,true );
        range=5;
        if (level == 1) {
            super.setDamage(130);
            super.setHp(340);
        } else if (level == 2) {
            super.setDamage(143);
            super.setHp(374);
        } else if (level == 3) {
            super.setDamage(157);
            super.setHp(411);
        } else if (level == 4) {
            super.setDamage(172);
            super.setHp(452);
        } else if (level == 5) {
            super.setDamage(189);
            super.setHp(496);
        }
    }
}
