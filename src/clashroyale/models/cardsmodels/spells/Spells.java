package clashroyale.models.cardsmodels.spells;

import clashroyale.models.cardsmodels.troops.Card;

/**
 * The type Spells.
 */
public class Spells extends Card {
    private double radious;

    /**
     * Instantiates a new Card.
     *
     * @param cost    the cost
     * @param radious the radious
     */
    public Spells(String title,int cost, double radious,String thumbImage) {
        super(title,cost,thumbImage);
        this.radious = radious ;
    }
}
