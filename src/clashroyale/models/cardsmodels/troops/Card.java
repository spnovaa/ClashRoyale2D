package clashroyale.models.cardsmodels.troops;

/**
 * The type Card.
 */
public class Card {
   private int cost;
   private String thumbImage ;

    /**
     * Instantiates a new Card.
     *
     * @param cost       the cost
     * @param thumbImage the thumb image
     */
    public Card(int cost,String thumbImage) {
        this.cost = cost;
        this.thumbImage=thumbImage;
    }
}
