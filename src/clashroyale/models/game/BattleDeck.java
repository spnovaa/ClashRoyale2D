package clashroyale.models.game;

import clashroyale.models.cardsmodels.buildings.Cannon;
import clashroyale.models.cardsmodels.spells.Fireball;
import clashroyale.models.cardsmodels.buildings.InfernoTower;
import clashroyale.models.cardsmodels.troops.*;
import clashroyale.models.cardsmodels.spells.Arrows;
import clashroyale.models.cardsmodels.spells.Rage;

import java.util.ArrayList;

/**
 * The type Battle deck.
 */
public class BattleDeck {
    private ArrayList<Card> allCards;
    private ArrayList<Card> chosenCards;

    /**
     * Instantiates a new Battle deck.
     *
     * @param level the level
     */
    public BattleDeck(int level) {
        allCards = new ArrayList<>();
        updateAllCards(level);
        chosenCards = new ArrayList<>();
    }

    /**
     * updates all cards list with the given
     *
     * @param level
     */
    private void updateAllCards(int level) {
        allCards.clear();
        String username = "";
        allCards.add(new ArchersCard(level, username));
        allCards.add(new BarbariansCard(level, username));
        allCards.add(new GiantCard(level, username));
        allCards.add(new BabyDragonCard(level, username));
        allCards.add(new ValkyrieCard(level, username));
        allCards.add(new MiniPEKKACard(level, username));
        allCards.add(new WizardCard(level, username));

        allCards.add(new Arrows(level, username));
        allCards.add(new Rage(level, username));

        allCards.add(new Cannon(level, username));
        allCards.add(new Fireball(level, username));
        allCards.add(new InfernoTower(level, username));
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
