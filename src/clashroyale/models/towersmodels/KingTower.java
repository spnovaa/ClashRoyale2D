package clashroyale.models.towersmodels;

/**
 * The type King tower.
 */
public class KingTower extends Tower {
    /**
     * Instantiates a new King tower.
     *
     * @param level the level
     */
    public KingTower(int level, String username, float centerX, float centerY, float radius, String title) {
        super(7, 1, username, centerX, centerY, radius, title);
        if (level == 1) {
            super.setDamage(50.0 / 15);
            super.setHp(2400);
        } else if (level == 2) {
            super.setDamage(53.0 / 15);
            super.setHp(2568);
        } else if (level == 3) {
            super.setDamage(57.0 / 15);
            super.setHp(2736);
        } else if (level == 4) {
            super.setDamage(60.0 / 15);
            super.setHp(2904);
        } else if (level == 5) {
            super.setDamage(64.0 / 15);
            super.setHp(3096);
        }
    }
}
