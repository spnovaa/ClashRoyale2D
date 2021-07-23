package clashroyale.models.cardsmodels.buildings;

import clashroyale.models.enums.Target;

/**
 * The type Inferno tower.
 */
public class InfernoTower extends Building {
    private int maxDamage;
    private int minDamage;

    /**
     * Instantiates a new Inferno tower.
     *
     * @param level       the level
     * @param relatedUser the related user
     */
    public InfernoTower(int level, String relatedUser) {
        super("infernoTower", 5, 6, 40*15, 0.4, Target.AIR_GROUND,
                "/thumbCards/thumbinferno-tower.png", relatedUser);
        if (level == 1) {
            super.setDamage(20);
            minDamage=20;
            super.setHp(800);
            maxDamage = 400;
        } else if (level == 2) {
            super.setDamage(22);
            minDamage=22;
            super.setHp(880);
            maxDamage = 440;
        } else if (level == 3) {
            super.setDamage(24);
            minDamage=24;
            super.setHp(968);
            maxDamage = 484;
        } else if (level == 4) {
            super.setDamage(26);
            minDamage=26;
            super.setHp(1064);
            maxDamage = 532;
        } else if (level == 5) {
            super.setDamage(29);
            minDamage = 29;
            super.setHp(1168);
            maxDamage = 584;
        }
        super.setPositionX(0);
        super.setPositionY(0);
    }

    /**
     * Gets max damage.
     *
     * @return the max damage
     */
    public int getMaxDamage() {
        return maxDamage;
    }

    /**
     * Gets min damage.
     *
     * @return the min damage
     */
    public int getMinDamage() {
        return minDamage;
    }
}
