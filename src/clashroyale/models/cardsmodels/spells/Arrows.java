package clashroyale.models.cardsmodels.spells;

/**
 * The type Arrows.
 */
public class Arrows extends Spells {
    private double areaDamage;

    /**
     * Instantiates a new Card.
     *
     * @param level the level
     */
    public Arrows(int level, String relatedUser) {
        super("arrows", 3, 4, "/thumbCards/thumbarrows.png", relatedUser);
        if (level == 1) {
            areaDamage = 144.0;
        } else if (level == 2) {
            areaDamage = 156.0;
        } else if (level == 3) {
            areaDamage = 174.0;
        } else if (level == 4) {
            areaDamage = 189.0;
        } else if (level == 5) {
            areaDamage = 210.0;
        }
        super.setPositionX(0);
        super.setPositionY(0);
    }

    public double getAreaDamage() {
        return areaDamage;
    }
}
