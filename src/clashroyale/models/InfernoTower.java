package clashroyale.models;

/**
 * The type Inferno tower.
 */
public class InfernoTower extends Building{
    /**
     * Instantiates a new Inferno tower.
     *
     * @param level the level
     */
    public InfernoTower(int level) {
        super(5, 6, 40, 0.4, Target.AIR_GROUND);
        if (level == 1) {
            super.setDamage(20-400);
            super.setHp(800);
        } else if (level == 2) {
            super.setDamage(22-440);
            super.setHp(880);
        } else if (level == 3) {
            super.setDamage(24-484);
            super.setHp(968);
        } else if (level == 4) {
            super.setDamage(26-532);
            super.setHp(1064);
        } else if (level == 5) {
            super.setDamage(29-584);
            super.setHp(1168);
        }
    }
}
