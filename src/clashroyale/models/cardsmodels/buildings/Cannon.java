package clashroyale.models.cardsmodels.buildings;

import clashroyale.models.enums.Target;

/**
 * The type Cannon.
 */
public class Cannon extends Building {

    /**
     * Instantiates a new Cannon.
     *
     * @param level the level
     */
    public Cannon(int level) {
        super(3, 5.5, 30, 0.8, Target.GROUND,"ClashRoyale2D\\src\\clashroyale\\resources\\thumbCards\\thumbcannon.png");
        if (level == 1) {
            super.setDamage(60);
            super.setHp(380);
        } else if (level == 2) {
            super.setDamage(66);
            super.setHp(418);
        } else if (level == 3) {
            super.setDamage(72);
            super.setHp(459);
        } else if (level == 4) {
            super.setDamage(79);
            super.setHp(505);
        } else if (level == 5) {
            super.setDamage(87);
            super.setHp(554);
        }
    }
}
