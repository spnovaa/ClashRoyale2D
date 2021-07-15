package clashroyale.models.cardsmodels.buildings;

import clashroyale.models.enums.Target;

/**
 * The type Inferno tower.
 */
public class InfernoTower extends Building {
    private int maxDamage;

    /**
     * Instantiates a new Inferno tower.
     *
     * @param level the level
     */
    public InfernoTower(int level) {
        super("infernoTower", 5, 6, 40, 0.4, Target.AIR_GROUND, "/thumbCards/thumbinferno-tower.png");
        if (level == 1) {
            super.setDamage(20);
            super.setHp(800);
            maxDamage=400;
        } else if (level == 2) {
            super.setDamage(22);
            super.setHp(880);
            maxDamage=440;
        } else if (level == 3) {
            super.setDamage(24);
            super.setHp(968);
            maxDamage=484;
        } else if (level == 4) {
            super.setDamage(26);
            super.setHp(1064);
            maxDamage=532;
        } else if (level == 5) {
            super.setDamage(29);
            super.setHp(1168);
            maxDamage = 584;
        }
    }
}
