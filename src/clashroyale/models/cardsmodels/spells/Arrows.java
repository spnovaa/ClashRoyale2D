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
    public Arrows(int level) {
        super(3, 4);
        if (level == 1) {
            areaDamage = 144;
        }
        else if (level==2){areaDamage=156;}
        else if (level==3){areaDamage=174;}
        else if (level==4){areaDamage=189;}
        else if (level==5){areaDamage=210;}
    }
}
