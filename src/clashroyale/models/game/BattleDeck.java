package clashroyale.models.game;

import clashroyale.models.cardsmodels.Card;

import java.util.ArrayList;

/**
 * The type Battle deck.
 */
public class BattleDeck {
    private ArrayList<Card> allCards;
    private ArrayList<Card> chosenCards;

    /**
     * Instantiates a new Battle deck.
     */
    public BattleDeck() {
        allCards = new ArrayList<>();
        chosenCards = new ArrayList<>();
    }

    /**
     * Gets all cards.
     *
     * @return the all cards
     */
    public ArrayList<Card> getAllCards() {
        return allCards;
    }

    /**
     * Sets all cards.
     *
     * @param allCards the all cards
     */
    public void setAllCards(ArrayList<Card> allCards) {
        this.allCards = allCards;
    }

    /**
     * Gets chosen cards.
     *
     * @return the chosen cards
     */
    public ArrayList<Card> getChosenCards() {
        return chosenCards;
    }

    /**
     * Sets chosen cards.
     *
     * @param chosenCards the chosen cards
     */
    public void setChosenCards(ArrayList<Card> chosenCards) {
        this.chosenCards = chosenCards;
    }
}
