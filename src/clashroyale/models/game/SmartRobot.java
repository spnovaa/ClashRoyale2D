package clashroyale.models.game;

import clashroyale.models.GameModel;
import clashroyale.models.cardsmodels.buildings.Building;
import clashroyale.models.cardsmodels.spells.Rage;
import clashroyale.models.cardsmodels.spells.Spells;
import clashroyale.models.cardsmodels.troops.Card;
import clashroyale.models.cardsmodels.troops.TroopsCard;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * The type Smart robot.
 */
public class SmartRobot extends Robot {
    private ArrayList<Card> smartBotCards;
    private GameModel gameModel;

    private int minX1 = 25;
    private int maxX1 = 183;
    private int minY1 = 35;
    private int maxY1 = 133;



    private int minX2 = 183;
    private int maxX2 = 340;
    private int minY2 = 35;
    private int maxY2 = 133;

    private int minX3 = 25;
    private int maxX3 = 183;
    private int minY3 = 133;
    private int maxY3 =  (int) (230-gameModel.getBridgesEndY());

    private int minX4 = 183;
    private int maxX4 = 340;
    private int minY4 = 133;
    private int maxY4 = (int) (230-gameModel.getBridgesEndY());

    private int minX5 = 25;
    private int maxX5 = 183;
    private int minY5 = (int) (230+gameModel.getBridgesStartY());
    private int maxY5 = 340;

    private int minX6 = 183;
    private int maxX6 = 340;
    private int minY6 = (int) (230+gameModel.getBridgesStartY());
    private int maxY6 = 340;

    private int minX7 = 25;
    private int maxX7 = 183;
    private int minY7 = 340;
    private int maxY7 = 450;

    private int minX8 = 183;
    private int maxX8 = 340;
    private int minY8 = 340;
    private int maxY8 = 450;

    public SmartRobot(int level) {
        super("smartBot", level);
        smartBotCards = choosingSmartBotCards();
    }

    public void setLiveData(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public ArrayList<Card> choosingSmartBotCards() {
        ArrayList<Card> smartBotCards1 = new ArrayList();
        int troop = 0;
        int spell = 0;
        int building = 0;
        for (Card card : super.getAllCards()) {
            if (card instanceof Building) {
                smartBotCards1.add(card);
            }
            break;
        }
        for (Card card : super.getAllCards()) {
            if (card instanceof TroopsCard) {
                smartBotCards1.add(card);
            }
            troop++;
            if (troop == 5) {
                break;
            }
        }
        for (Card card : super.getAllCards()) {
            if (card instanceof Spells) {
                smartBotCards1.add(card);
            }
            spell++;
            if (spell == 2) {
                break;
            }
        }
        Collections.shuffle(smartBotCards1);
        return smartBotCards1;
    }

    public Card chooseCardToPlay() {

        Card card = smartBotCards.get(new Random().nextInt(8));
        if (card.getCost() < getElixirCount()) {
            return card;
        } else return null;
    }

    public float getRandomNumberUsingNextInt(float min, float max) {
        Random random = new Random();
        return random.nextFloat() * (max - min) + min;
    }

    public Point2D chooseCoordinatesToPlay(Card botChosenCard) {
        int enemy1 = 0;
        int enemy2 = 0;
        int enemy3 = 0;
        int enemy4 = 0;

        float deployedY=0,deployedX=0;
        if (botChosenCard instanceof Rage) {
            for (TroopsCard troopsCard : gameModel.getArenaExistingTroops()) {
             if (troopsCard.getRelatedUser().equals("smartBot")){
                 deployedX=troopsCard.getCenterPositionX()+1;
                 deployedY=troopsCard.getCenterPositionY()+1;
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
                    enemy3++;
                }
                int maxEnemy = Math.max(Math.max(enemy1,enemy2),Math.max(enemy3,enemy4));
                if (enemy1 == maxEnemy) {
                    deployedX = getRandomNumberUsingNextInt(minX1,maxX1);
                    deployedY = getRandomNumberUsingNextInt(minY1,maxY1);
                } else if (enemy2 == maxEnemy){
                    deployedX = getRandomNumberUsingNextInt(minX2,maxX2);
                    deployedY = getRandomNumberUsingNextInt(minY2,maxY2);
                }
                else if (enemy3 == maxEnemy){
                    deployedX = getRandomNumberUsingNextInt(minX3,maxX3);
                    deployedY = getRandomNumberUsingNextInt(minY3,maxY3);
                }
                else if (enemy4 == maxEnemy){
                    deployedX = getRandomNumberUsingNextInt(minX4,maxX4);
                    deployedY = getRandomNumberUsingNextInt(minY4,maxY4);
                }

            }
            }


            return new Point2D(deployedX,deployedY);
        }
    }
