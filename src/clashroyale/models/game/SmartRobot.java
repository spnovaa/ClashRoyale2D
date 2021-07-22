package clashroyale.models.game;

import clashroyale.models.GameModel;
import clashroyale.models.cardsmodels.buildings.Building;
import clashroyale.models.cardsmodels.buildings.Cannon;
import clashroyale.models.cardsmodels.buildings.InfernoTower;
import clashroyale.models.cardsmodels.spells.Arrows;
import clashroyale.models.cardsmodels.spells.Fireball;
import clashroyale.models.cardsmodels.spells.Rage;
import clashroyale.models.cardsmodels.spells.Spells;
import clashroyale.models.cardsmodels.troops.*;
import javafx.geometry.Point2D;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * The type Smart robot.
 */
public class SmartRobot extends Robot {
    private ArrayList<Card> smartBotCards;
    private GameModel gameModel;

    private int minX1;
    private int maxX1;
    private int minY1;
    private int maxY1;


    private int minX2;
    private int maxX2;
    private int minY2;
    private int maxY2;

    private int minX3;
    private int maxX3;
    private int minY3;
    private int maxY3;

    private int minX4;
    private int maxX4;
    private int minY4;
    private int maxY4;

    private int minX5;
    private int maxX5;
    private int minY5;
    private int maxY5;

    private int minX6;
    private int maxX6;
    private int minY6;
    private int maxY6;

    private int minX7;
    private int maxX7;
    private int minY7;
    private int maxY7;

    private int minX8;
    private int maxX8;
    private int minY8;
    private int maxY8;

    /**
     * Instantiates a new Smart robot.
     *
     * @param level the level
     */
    public SmartRobot(int level) {
        super("smartBot", level);
        smartBotCards = choosingSmartBotCards();
    }

    /**
     * Sets live data.
     *
     * @param gameModel the game model
     */
    public void setLiveData(GameModel gameModel) {
        this.gameModel = gameModel;
        initializePosition();
    }

    private void initializePosition() {
        minX1 = 25;
        maxX1 = 183;
        minY1 = 35;
        maxY1 = 133;


        minX2 = 183;
        maxX2 = 340;
        minY2 = 35;
        maxY2 = 133;

        minX3 = 25;
        maxX3 = 183;
        minY3 = 133;
        maxY3 = (int) (230 - gameModel.getBridgesEndY());

        minX4 = 183;
        maxX4 = 340;
        minY4 = 133;
        maxY4 = (int) (230 - gameModel.getBridgesEndY());

        minX5 = 25;
        maxX5 = 183;
        minY5 = (int) (230 + gameModel.getBridgesStartY());
        maxY5 = 340;

        minX6 = 183;
        maxX6 = 340;
        minY6 = (int) (230 + gameModel.getBridgesStartY());
        maxY6 = 340;

        minX7 = 25;
        maxX7 = 183;
        minY7 = 340;
        maxY7 = 450;

        minX8 = 183;
        maxX8 = 340;
        minY8 = 340;
        maxY8 = 450;
    }

    /**
     * Choosing smart bot cards array list.
     *
     * @return the array list
     */
    public synchronized ArrayList<Card> choosingSmartBotCards() {
//        ArrayList<Card> cards = new ArrayList<>(super.getAllCards());
//        return cards;
        String username = "smartBot";
        ArrayList<Card> allCards = new ArrayList<>();
        allCards.add(new ArchersCard(getLevel(), username));
        allCards.add(new BarbariansCard(getLevel(), username));
        allCards.add(new BabyDragonCard(getLevel(), username));
        allCards.add(new GiantCard(getLevel(), username));
        allCards.add(new MiniPEKKACard(getLevel(), username));
        allCards.add(new ValkyrieCard(getLevel(), username));
        allCards.add(new WizardCard(getLevel(), username));
        allCards.add(new Cannon(getLevel(), username));
        allCards.add(new InfernoTower(getLevel(), username));
        allCards.add(new Fireball(getLevel(), username));
        allCards.add(new Rage(getLevel(), username));
        allCards.add(new Arrows(getLevel(), username));
        ArrayList<Card> smartBotCards1 = new ArrayList<>();
        int troop = 0;
        int spell = 0;

        for (Card card : allCards)
            if (card instanceof Spells && spell != 2) {
                smartBotCards1.add(card);
                spell++;
            }

        for (Card card : allCards) {
            if (card instanceof Building) {
                smartBotCards1.add(card);
                break;
            }
        }

        for (Card card : allCards) {
            if (card instanceof TroopsCard && troop != 5) {
                smartBotCards1.add(card);
                troop++;
            }
        }
        Collections.shuffle(smartBotCards1);
        return smartBotCards1;
    }

    /**
     * Choose card to play card.
     *
     * @return the card
     */
    public Card chooseCardToPlay() {
        SecureRandom secureRandom = new SecureRandom();
        Card card = smartBotCards.get(secureRandom.nextInt(8));
        if (card.getCost() < getElixirCount()) {
            return card;
        } else return null;
    }

    /**
     * Gets random number using next int.
     *
     * @param min the min
     * @param max the max
     * @return the random number using next int
     */
    public float getRandomNumberUsingNextInt(float min, float max) {
        Random random = new Random();
        return random.nextFloat() * (max - min) + min;
    }

    /**
     * Choose coordinates to play point 2 d.
     *
     * @param botChosenCard the bot chosen card
     * @return the point 2 d
     */
    public Point2D chooseCoordinatesToPlay(Card botChosenCard) {
        int enemy1 = 0;
        int enemy2 = 0;
        int enemy3 = 0;
        int enemy4 = 0;

        float deployedY = 0, deployedX = 0;
        if (botChosenCard instanceof Rage) {
            for (TroopsCard troopsCard : gameModel.getArenaExistingTroops()) {
                if (troopsCard.getRelatedUser().equals("smartBot")) {
                    deployedX = troopsCard.getCenterPositionX() + 1;
                    deployedY = troopsCard.getCenterPositionY() + 1;
                }
            }
        } else {

            for (TroopsCard troopsCard : gameModel.getArenaExistingTroops()) {
                if (!troopsCard.getRelatedUser().equals("smartBot") && troopsCard.getCenterPositionX() > minX1 && troopsCard.getCenterPositionX() < maxX1 && troopsCard.getCenterPositionY() < maxY1 && troopsCard.getCenterPositionY() > minY1) {
                    enemy1++;
                }
                if (!troopsCard.getRelatedUser().equals("smartBot") && troopsCard.getCenterPositionX() > minX2 && troopsCard.getCenterPositionX() < maxX2 && troopsCard.getCenterPositionY() < maxY2 && troopsCard.getCenterPositionY() > minY2) {
                    enemy2++;
                }
                if (!troopsCard.getRelatedUser().equals("smartBot") && troopsCard.getCenterPositionX() > minX3 && troopsCard.getCenterPositionX() < maxX3 && troopsCard.getCenterPositionY() < maxY3 && troopsCard.getCenterPositionY() > minY3) {
                    enemy3++;
                }
                if (!troopsCard.getRelatedUser().equals("smartBot") && troopsCard.getCenterPositionX() > minX4 && troopsCard.getCenterPositionX() < maxX4 && troopsCard.getCenterPositionY() < maxY4 && troopsCard.getCenterPositionY() > minY4) {
                    enemy4++;
                }
            }
            int maxEnemy = Math.max(Math.max(enemy1, enemy2), Math.max(enemy3, enemy4));
            if (enemy1 == maxEnemy) {
                deployedX = getRandomNumberUsingNextInt(minX1, maxX1);
                deployedY = getRandomNumberUsingNextInt(minY1, maxY1);
            } else if (enemy2 == maxEnemy) {
                deployedX = getRandomNumberUsingNextInt(minX2, maxX2);
                deployedY = getRandomNumberUsingNextInt(minY2, maxY2);
            } else if (enemy3 == maxEnemy) {
                deployedX = getRandomNumberUsingNextInt(minX3, maxX3);
                deployedY = getRandomNumberUsingNextInt(minY3, maxY3);
            } else if (enemy4 == maxEnemy) {
                deployedX = getRandomNumberUsingNextInt(minX4, maxX4);
                deployedY = getRandomNumberUsingNextInt(minY4, maxY4);
            }


        }


        return new Point2D(deployedX, deployedY);
    }
}
