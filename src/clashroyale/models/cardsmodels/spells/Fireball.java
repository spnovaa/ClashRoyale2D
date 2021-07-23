package clashroyale.models.cardsmodels.spells;

/**
 * The type Fireball.
 */
public class Fireball extends Spells {
    private double areaDamage;

    /**
     * Instantiates a new Card.
     *
     * @param level       the level
     * @param relatedUser the related user
     */
    public Fireball(int level, String relatedUser) {
        super("fireball", 4, 2.5, "/thumbCards/thumbfireball.png", relatedUser);
        if (level == 1) {
            areaDamage = 325;
        } else if (level == 2) {
            areaDamage = 357;
        } else if (level == 3) {
            areaDamage = 393;
        } else if (level == 4) {
            areaDamage = 432;
        } else if (level == 5) {
            areaDamage = 474;
        }
        super.setPositionX(0);
        super.setPositionY(0);
    }

    /**
     * Gets area damage.
     *
     * @return the area damage
     */
    public double getAreaDamage() {
        return areaDamage;
    }

    /**
     * Sets area damage.
     *
     * @param areaDamage the area damage
     */
    public void setAreaDamage(double areaDamage) {
        this.areaDamage = areaDamage;
    }
}
