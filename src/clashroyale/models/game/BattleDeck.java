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

        allCards.add(new ArchersCard(level));
        allCards.add(new BarbariansCard(level));
        allCards.add(new GiantCard(level));
        allCards.add(new BabyDragonCard(level));
        allCards.add(new ValkyrieCard(level));
        allCards.add(new MiniPEKKACard(level));
        allCards.add(new WizardCard(level));

        allCards.add(new Arrows(level));
        allCards.add(new Rage(level));

        allCards.add(new Cannon(level));
        allCards.add(new Fireball(level));
        allCards.add(new InfernoTower(level));
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
