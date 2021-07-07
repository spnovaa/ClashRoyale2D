package clashroyale.models.cardsmodels;

import clashroyale.models.enums.Range;
import clashroyale.models.enums.Speed;
import clashroyale.models.enums.Target;

/**
 * The type Mini pekka card.
 */
public class MiniPEKKACard extends TroopsCard {
    /**
     * Instantiates a new Mini pekka card.
     *
     * @param level the level
     */
    public MiniPEKKACard(int level) {
        super(4, 1, 1.8, Target.GROUND, Range.MELEE, Speed.FAST, false);
        if (level == 1) {
            super.setDamage(325);
            super.setHp(600);
        } else if (level == 2) {
            super.setDamage(357);
            super.setHp(660);
        } else if (level == 3) {
            super.setDamage(393);
            super.setHp(726);
        } else if (level == 4) {
            super.setDamage(432);
            super.setHp(798);
        } else if (level == 5) {
            super.setDamage(474);
            super.setHp(876);
        }
    }
}
