package clashroyale.models.cardsmodels;

import clashroyale.models.enums.Range;
import clashroyale.models.enums.Speed;
import clashroyale.models.enums.Target;

/**
 * The type Archers card.
 */
public class ArchersCard extends TroopsCard {
    private int range;

    /**
     * Instantiates a new Archers card.
     *
     * @param level the level
     */
    public ArchersCard(int level) {
        super(3, 2, 1.2, Target.AIR_GROUND, Range.RAMGED, Speed.MEDIUM, false);
        range = 5;
        if (level == 1) {
            super.setDamage(33);
            super.setHp(125);
        } else if (level == 2) {
            super.setDamage(44);
            super.setHp(127);
        } else if (level == 3) {
            super.setDamage(48);
            super.setHp(151);
        } else if (level == 4) {
            super.setDamage(53);
            super.setHp(166);
        } else if (level == 5) {
            super.setDamage(58);
            super.setHp(182);
        }
    }
}
