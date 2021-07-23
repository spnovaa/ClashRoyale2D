package clashroyale.models.game;

import clashroyale.models.cardsmodels.buildings.Cannon;
import clashroyale.models.cardsmodels.buildings.InfernoTower;
import clashroyale.models.cardsmodels.spells.Arrows;
import clashroyale.models.cardsmodels.spells.Fireball;
import clashroyale.models.cardsmodels.spells.Rage;
import clashroyale.models.cardsmodels.troops.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;


/**
 * The type Robot.
 */
public class Robot {
    private String username;
    private Queue<Card> cardsQue;
    private ArrayList<Card> allCards;
    private float minY;
    private float minX;
    private float maxY;
    private float maxX;
    private int level;
    private int cupsCount;
    private int elixirCount;

    /**
     * Instantiates a new Robot.
     *
     * @param username the username
     * @param level    the level
     */
    public Robot(String username, int level) {
        this.username = username;
        this.level = level;
        cardsQue = new LinkedList<>();
        allCards = new ArrayList<>();
        allCards = initializeAllCards();
        shuffleCardsList();
        chooseCardsToPlay();
        minY = 35;
        maxY = 230;
        minX = 25;
        maxX = 340;
        elixirCount = 4;
    }

    private void chooseCardsToPlay() {
        for (int i = 0; i < 8; i++) {
            cardsQue.add(allCards.get(i));
//            System.out.println("added "+allCards.get(i).getTitle()+" for " +  allCards.get(i).getRelatedUser());
        }
    }

    private void shuffleCardsList() {
        Collections.shuffle(allCards);
    }

    private ArrayList<Card> initializeAllCards() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new ArchersCard(getLevel(), username));
        cards.add(new BarbariansCard(getLevel(), username));
        cards.add(new BabyDragonCard(getLevel(), username));
        cards.add(new GiantCard(getLevel(), username));
        cards.add(new MiniPEKKACard(getLevel(), username));
        cards.add(new ValkyrieCard(getLevel(), username));
        cards.add(new WizardCard(getLevel(), username));
        cards.add(new Cannon(getLevel(), username));
        cards.add(new InfernoTower(getLevel(), username));
        cards.add(new Fireball(getLevel(), username));
        cards.add(new Rage(getLevel(), username));
        cards.add(new Arrows(getLevel(), username));
        return cards;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets cards que.
     *
     * @return the cards que
     */
    public Queue<Card> getCardsQue() {
        return cardsQue;
    }

    /**
     * Sets cards que.
     *
     * @param cardsQue the cards que
     */
    public void setCardsQue(Queue<Card> cardsQue) {
        this.cardsQue = cardsQue;
    }

    /**
     * All cards array list.
     *
     * @return the array list
     */
    public ArrayList<Card> allCards() {
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
     * Gets min y.
     *
     * @return the min y
     */
    public float getMinY() {
        return minY;
    }

    /**
     * Sets min y.
     *
     * @param minY the min y
     */
    public void setMinY(float minY) {
        this.minY = minY;
    }

    /**
     * Gets min x.
     *
     * @return the min x
     */
    public float getMinX() {
        return minX;
    }

    /**
     * Sets min x.
     *
     * @param minX the min x
     */
    public void setMinX(float minX) {
        this.minX = minX;
    }

    /**
     * Gets max y.
     *
     * @return the max y
     */
    public float getMaxY() {
        return maxY;
    }

    /**
     * Sets max y.
     *
     * @param maxY the max y
     */
    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    /**
     * Gets max x.
     *
     * @return the max x
     */
    public float getMaxX() {
        return maxX;
    }

    /**
     * Sets max x.
     *
     * @param maxX the max x
     */
    public void setMaxX(float maxX) {
        this.maxX = maxX;
    }

    /**
     * Gets cups count.
     *
     * @return the cups count
     */
    public int getCupsCount() {
        return cupsCount;
    }

    /**
     * Sets cups count.
     *
     * @param cupsCount the cups count
     */
    public void setCupsCount(int cupsCount) {
        this.cupsCount = cupsCount;
    }

    /**
     * Gets elixir count.
     *
     * @return the elixir count
     */
    public int getElixirCount() {
        return elixirCount;
    }

    /**
     * Sets elixir count.
     *
     * @param elixirCount the elixir count
     */
    public void setElixirCount(int elixirCount) {
        this.elixirCount = elixirCount;
    }
}
