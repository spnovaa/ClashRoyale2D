package clashroyale.models;

/**
 * The type Spells.
 */
public class Spells extends Card{
    private double radious;

    /**
     * Instantiates a new Card.
     *
     * @param cost    the cost
     * @param radious the radious
     */
    public Spells(int cost, double radious) {
        super(cost);
        this.radious = radious ;
    }
}
