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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Queue<Card> getCardsQue() {
        return cardsQue;
    }

    public void setCardsQue(Queue<Card> cardsQue) {
        this.cardsQue = cardsQue;
    }

    public ArrayList<Card> allCards() {
        return allCards;
    }

    public void setAllCards(ArrayList<Card> allCards) {
        this.allCards = allCards;
    }

    public float getMinY() {
        return minY;
    }

    public void setMinY(float minY) {
        this.minY = minY;
    }

    public float getMinX() {
        return minX;
    }

    public void setMinX(float minX) {
        this.minX = minX;
    }

    public float getMaxY() {
        return maxY;
    }

    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    public float getMaxX() {
        return maxX;
    }

    public void setMaxX(float maxX) {
        this.maxX = maxX;
    }

    public int getCupsCount() {
        return cupsCount;
    }

    public void setCupsCount(int cupsCount) {
        this.cupsCount = cupsCount;
    }

    public int getElixirCount() {
        return elixirCount;
    }

    public void setElixirCount(int elixirCount) {
        this.elixirCount = elixirCount;
    }
}
