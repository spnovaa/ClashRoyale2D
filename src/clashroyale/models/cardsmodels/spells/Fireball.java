package clashroyale.models.cardsmodels.spells;

/**
 * The type Fireball.
 */
public class Fireball extends Spells {
    private double areaDamage;

    /**
     * Instantiates a new Card.
     *
     * @param level the level
     */
    public Fireball(int level) {
        super("fireball", 4, 2.5, "/thumbCards/thumbfireball.png");
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

}
