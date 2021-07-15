package clashroyale.models.cardsmodels.troops;

import clashroyale.models.enums.Range;
import clashroyale.models.enums.Speed;
import clashroyale.models.enums.Target;

/**
 * The type Giant.
 */
public class GiantCard extends TroopsCard {
    /**
     * Instantiates a new Giant.
     *
     * @param level the level
     */
    public GiantCard(int level) {
        super("giant", 5, 1, 1.5, Target.BUILDINGS, Range.MELEE, Speed.SLOW, false, "/thumbCards/thumbgiant.png");
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