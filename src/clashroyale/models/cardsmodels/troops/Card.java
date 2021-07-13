package clashroyale.models.cardsmodels.troops;

/**
 * The type Card.
 */
public class Card {
    private String title;
   private int cost;
   private String thumbImage ;

    /**
     * Instantiates a new Card.
     *
     * @param cost       the cost
     * @param thumbImage the thumb image
     */
    public Card(String title,int cost,String thumbImage) {
       this.title=title;
        this.cost = cost;
        this.thumbImage=thumbImage;
    }

    /**
     * Gets thumb image.
     *
     * @return the thumb image
     */
    public String getThumbImage() {
        return thumbImage;
    }

    /**
     * Gets cost.
     *
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    public String getTitle() {
        return title;
    }
}
