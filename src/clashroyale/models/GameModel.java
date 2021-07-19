package clashroyale.models;

import clashroyale.models.cardsmodels.buildings.Building;
import clashroyale.models.cardsmodels.troops.Card;
import clashroyale.models.cardsmodels.troops.TroopsCard;
import clashroyale.models.enums.Range;
import clashroyale.models.enums.Speed;
import clashroyale.models.towersmodels.KingTower;
import clashroyale.models.towersmodels.QueenTower;
import clashroyale.models.towersmodels.Tower;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GameModel {
    private UserModel userModel;

    private int playerOneCrowns;
    private int playerTwoCrowns;
    private int playerOneLostHP;
    private int playerTwoLostHP;

    private final float unitSize = 20;

    private ArrayList<TroopsCard> arenaExistingTroops;
    private ArrayList<Card> arenaExistingSpellCards;
    private ArrayList<Tower> arenaExistingTowers;
    private ArrayList<Building> arenaExistingBuildings;

    private KingTower userKingTower;
    private QueenTower userRightQueenTower;
    private QueenTower userLeftQueenTower;

    private int userLevel;
    private String playerOneUsername;

    private KingTower botKingTower;
    private QueenTower botRightQueenTower;
    private QueenTower botLeftQueenTower;
    private String botUsername;

    private float minDist;
    private float fieldCenterX;
    private float bridgeWidth;
    private float bridgesStartY;     //-> bottom
    private float bridgesEndY;       //-> top

    private float leftBridgeX;
    private float rightBridgeX;

    private float playerOneRightBorderY;
    private float playerOneLeftBorderY;

    private float playerTwoRightBorderY;
    private float playerTwoLeftBorderY;

    private float slowStepSize;
    private float mediumStepSize;
    private float fastStepSize;

    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //---------------------------------Constructor And Initialization------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------

    public GameModel(UserModel userModel) {
        this.userModel = userModel;
        this.playerOneUsername = userModel.getUsername();
        this.botUsername = "simpleBot";

        minDist = 1;

        arenaExistingTroops = new ArrayList<>();
        arenaExistingSpellCards = new ArrayList<>();
        arenaExistingTowers = new ArrayList<>();
        arenaExistingBuildings = new ArrayList<>();

        playerOneCrowns = 0;
        playerTwoCrowns = 0;
        playerTwoLostHP = 0;
        playerOneLostHP = 0;

        slowStepSize = 10;
        mediumStepSize = 15;
        fastStepSize = 20;

        userLevel = userModel.getLevel();
        initializeBorders();
        initializeUserTowers();
        initializeBotTowers();
        initializeBridges();
    }

    private void initializeBridges() {
        fieldCenterX = 190;

        bridgeWidth = 30;
        bridgesStartY = 260;
        bridgesEndY = bridgesStartY - bridgeWidth;

        leftBridgeX = 85;
        rightBridgeX = 275;

    }

    private void initializeUserTowers() {
        userKingTower = new KingTower(userLevel, playerOneUsername, 180, 420, 35);
        userRightQueenTower = new QueenTower(userLevel, playerOneUsername, 270, 375, 20);
        userLeftQueenTower = new QueenTower(userLevel, playerOneUsername, 85, 375, 20);
        arenaExistingTowers.add(userKingTower);
        arenaExistingTowers.add(userRightQueenTower);
        arenaExistingTowers.add(userLeftQueenTower);
    }

    private void initializeBotTowers() {
        botKingTower = new KingTower(userLevel, botUsername, 180, 75, 35);
        botRightQueenTower = new QueenTower(userLevel, botUsername, 270, 115, 20);
        botLeftQueenTower = new QueenTower(userLevel, botUsername, 85, 115, 20);
        arenaExistingTowers.add(botKingTower);
        arenaExistingTowers.add(botRightQueenTower);
        arenaExistingTowers.add(botLeftQueenTower);
    }

    private void initializeBorders() {
        playerOneRightBorderY = 255;
        playerOneLeftBorderY = 255;

        playerTwoLeftBorderY = 225;
        playerTwoRightBorderY = 225;
    }
    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //------------------------------------------Data Managing--------------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------

    public void updateGameModel() {
        for (TroopsCard card : arenaExistingTroops) {
            attackNearestEnemyInRange(card);
            float cardX = card.getCenterPositionX();
            float cardY = card.getCenterPositionY();

            if (cardY > bridgesStartY && !card.getTitle().equals("babyDragon")) {//card is in user's field and moves on the ground
                if (cardX >= fieldCenterX) {
                    moveCardToRightBridge(card);            //card is in right half
                } else {                                    //card is in left half
                    moveCardToLeftBridge(card);
                }

            } else if (cardY < bridgesEndY || card.getTitle().equals("babyDragon")) {//card is in enemy's field or moves on air
                if (cardX >= fieldCenterX) {                //card is in the right side
                    if (botRightQueenTower.isAlive())       // right queen tower is alive and card is in right side
                        moveCardToRightQueenTower(card);
                    else {                                  //card is in right side but right queen tower is dead
                        if (botLeftQueenTower.isAlive())    //if left queen tower stands, move in that direction
                            moveCardToLeftQueenTower(card);
                        else                                //both queen towers are gone. move in king towers direction
                            moveCardToKingTower(card);
                    }
                } else {                                    //card is in the left side of the field
                    if (botLeftQueenTower.isAlive())        // left queen tower is alive and card is in left side
                        moveCardToLeftQueenTower(card);
                    else {                                  //card is in right side but right queen tower is dead
                        if (botRightQueenTower.isAlive())   //if right queen tower stands, move in that direction
                            moveCardToRightQueenTower(card);
                        else                                //both queen towers are gone. move in king towers direction
                            moveCardToKingTower(card);
                    }
                }
            } else {                                        //card is on the bridge
                if (cardX >= fieldCenterX) {
                    passCardFromRightBridge(card);
                } else
                    passCardFromLeftBridge(card);
            }
        }
    }

    private void moveCardToRightBridge(TroopsCard card) {
        stepToTarget(card, rightBridgeX, bridgesStartY, true);
//        System.out.println("Moving Card To Right Bridge");
    }

    private void moveCardToLeftBridge(TroopsCard card) {
        stepToTarget(card, leftBridgeX, bridgesStartY, true);
//        System.out.println("Moving Card To Left Bridge");
    }

    private void passCardFromRightBridge(TroopsCard cardToMove) {
        stepToTarget(cardToMove, rightBridgeX, bridgesEndY, true);
        System.out.println("Passing Card from Right Bridge");
    }

    private void passCardFromLeftBridge(TroopsCard cardToMove) {
        stepToTarget(cardToMove, leftBridgeX, bridgesEndY, true);
//        System.out.println("Passing Card From Left Bridge");
    }

    private void moveCardToLeftQueenTower(TroopsCard cardToMove) {
        stepToTarget(cardToMove, botLeftQueenTower.getCenterPositionX(), botLeftQueenTower.getCenterPositionY(), false);
//        System.out.println("Moving Card To Left Queen");
    }

    private void moveCardToRightQueenTower(TroopsCard cardToMove) {
        stepToTarget(cardToMove, botRightQueenTower.getCenterPositionX(), botRightQueenTower.getCenterPositionY(), false);
//        System.out.println("Moving Card To Right Queen");
    }

    private void moveCardToKingTower(TroopsCard cardToMove) {
        stepToTarget(cardToMove, botKingTower.getCenterPositionX(), botKingTower.getCenterPositionY(), false);
//        System.out.println("Moving Card To King");
    }

    private synchronized void stepToTarget(TroopsCard card, float targetX, float targetY, boolean isPassable) {
        float currentX = card.getCenterPositionX();
        float currentY = card.getCenterPositionY();
        float rangeSize = getRangeSize(card.getRangeType());
        float speedSize = getSpeedSize(card.getSpeed());
        Point2D current = new Point2D(currentX, currentY);
        Point2D target = new Point2D(targetX, targetY);
        if (current.distance(target) > minDist) {
            //if card's range is farther than targets center
            double distance = current.distance(target);
//            System.out.println("target:" + targetX + "," + targetY);
//            System.out.println("current:" + currentX + "," + currentY);
//            System.out.println("distance:" + distance + "\n\n");

            float distanceX = targetX - currentX;
            float distanceY = targetY - currentY;
//            System.out.println("distance: x:" + distanceX + " y: " + distanceY);
            double xRatio = distanceX / distance;
            double yRatio = distanceY / distance;

            float stepX = (float) (speedSize * xRatio);
            float stepY = (float) (speedSize * yRatio);
//            System.out.println("stepX: "+ stepX + " StepY: "+stepY);
            changeRouteIfBlocked(card, speedSize);

            currentX = card.getCenterPositionX();
            currentY = card.getCenterPositionY();

            card.setCenterPositionX(currentX + stepX);
            card.setCenterPositionY(currentY + stepY);
        } else if (isPassable) {
            String user = card.getRelatedUser();
            if (!(user.equals("simpleBot") || user.equals("smartBot"))) {
                card.setCenterPositionX(targetX);
                card.setCenterPositionY(targetY - 1);
            } else {
                card.setCenterPositionX(targetX);
                card.setCenterPositionY(targetY + 1);
            }

        }
    }

    private void attackNearestEnemyInRange(Card card) {
        float distanceToTower = 10000;
        float distanceToCard = 10000;

        Card enemyCard = findNearestEnemyInRange(card);
        Tower enemyTower = findNearestEnemyTowerInRange(card);
        Point2D cardPosition = new Point2D(card.getCenterPositionX(), card.getCenterPositionY());

        if (enemyCard != null) {
            Point2D enemyCardPosition = new Point2D(enemyCard.getCenterPositionX(), enemyCard.getCenterPositionY());
            distanceToCard = (float) enemyCardPosition.distance(cardPosition);
        }
        if (enemyTower != null) {
            Point2D enemyTowerPosition = new Point2D(enemyTower.getCenterPositionX(), enemyTower.getCenterPositionY());
            distanceToTower = (float) enemyTowerPosition.distance(cardPosition);
        }


        if (distanceToTower < distanceToCard && distanceToTower <= getRangeSize(((TroopsCard) card).getRangeType())) {
            attackCardToTower(card, enemyTower);
        } else if (distanceToCard <= getRangeSize(((TroopsCard) card).getRangeType())) {
            attackCardToCard(card, enemyCard);
        }


    }

    private Tower findNearestEnemyTowerInRange(Card card) {

        Point2D cardPosition = new Point2D(card.getCenterPositionX(), card.getCenterPositionY());
        Point2D botRightQueenPosition = new Point2D(botRightQueenTower.getCenterPositionX(), botRightQueenTower.getCenterPositionY());
        Point2D botLeftQueenPosition = new Point2D(botLeftQueenTower.getCenterPositionX(), botLeftQueenTower.getCenterPositionY());
        Point2D botKingPosition = new Point2D(botKingTower.getCenterPositionX(), botKingTower.getCenterPositionY());

        float botRightQueenDistance = (float) cardPosition.distance(botRightQueenPosition);
        float botLeftQueenDistance = (float) cardPosition.distance(botLeftQueenPosition);
        float botKingDistance = (float) cardPosition.distance(botKingPosition);

        Float[] distances = {botKingDistance, botLeftQueenDistance, botRightQueenDistance};
        float min = Collections.min(Arrays.asList(distances));

        if (min <= getRangeSize(((TroopsCard) card).getRangeType())) {
            if (min == botKingDistance)
                return botKingTower;
            if (min == botRightQueenDistance)
                return botRightQueenTower;
            if (min == botLeftQueenDistance)
                return botLeftQueenTower;
        }

        //if no enemy tower found in range, returns null
        return null;
    }

    private Card findNearestEnemyInRange(Card card) {
        Card nearestEnemy = null;
        float leastDistance;

        if (card instanceof TroopsCard)
            leastDistance = getRangeSize(((TroopsCard) card).getRangeType());
        else if (card instanceof Building)
            leastDistance = (float) ((Building) card).getRange();
        else
            leastDistance = 10000;

        for (TroopsCard troopsCard : arenaExistingTroops) {
            if (!troopsCard.getRelatedUser().equals(card.getRelatedUser())) {
                float distance = calcDistance(card, troopsCard);
                if (distance < leastDistance && troopsCard.isAlive()) {
                    nearestEnemy = troopsCard;
                    leastDistance = distance;
                }
            }
        }
        for (Building building : arenaExistingBuildings) {
            if (!building.getRelatedUser().equals(card.getRelatedUser())) {
                float distance = calcDistance(card, building);
                if (distance < leastDistance && building.isAlive()) {
                    nearestEnemy = building;
                    leastDistance = distance;
                }
            }
        }

        return nearestEnemy;
    }

    private float calcDistance(Card card1, Card card2) {
        Point2D point1 = new Point2D(card1.getCenterPositionX(), card1.getCenterPositionY());
        Point2D point2 = new Point2D(card2.getCenterPositionX(), card2.getCenterPositionY());
        return (float) point1.distance(point2);
    }

    private void attackCardToTower(Card attackerCard, Tower targetTower) {
        System.out.println(attackerCard.getTitle() + " is Attacking " + targetTower.getUuid());
        int hp = 0;
        if (attackerCard instanceof TroopsCard) {
            System.out.println("troop damage :" + ((TroopsCard) attackerCard).getDamage());
            System.out.println("TowerHp : " + targetTower.getHp());
            hp = targetTower.getHp() - ((TroopsCard) attackerCard).getDamage();
        } else if (attackerCard instanceof Building) {
            hp = targetTower.getHp() - ((Building) attackerCard).getDamage();
        }
        System.out.println(" Tower Hp : " + hp);
        if (hp >= 0)
            targetTower.setHp(hp);
        else {
            targetTower.setHp(0);
            targetTower.setAlive(false);
            System.out.println("Tower " + targetTower.getUuid() + " is now dead");
        }

    }

    private void attackTowerToCard(Tower attackerTower, Card targetCard) {
        if (targetCard instanceof TroopsCard) {
            ((TroopsCard) targetCard).setHp(((TroopsCard) targetCard).getHp() - attackerTower.getDamage());
        } else if (targetCard instanceof Building) {
            ((Building) targetCard).setHp(((Building) targetCard).getHp() - attackerTower.getDamage());
        }
    }

    private void attackCardToCard(Card attackerCard, Card targetCard) {

        if (targetCard instanceof TroopsCard) {
            if (attackerCard instanceof TroopsCard) {
                ((TroopsCard) targetCard).setHp(((TroopsCard) targetCard).getHp() - ((TroopsCard) attackerCard).getDamage());
            } else if (attackerCard instanceof Building) {
                ((TroopsCard) targetCard).setHp(((TroopsCard) targetCard).getHp() - ((Building) attackerCard).getDamage());
            }
        } else if (targetCard instanceof Building) {
            if (attackerCard instanceof TroopsCard) {
                ((Building) targetCard).setHp(((Building) targetCard).getHp() - ((TroopsCard) attackerCard).getDamage());
            } else if (attackerCard instanceof Building) {
                ((Building) targetCard).setHp(((Building) targetCard).getHp() - ((Building) attackerCard).getDamage());
            }
        }
    }

    private void attackTowerToTower(Tower attackerTower, Tower targetTower) {
        targetTower.setHp(targetTower.getHp() - attackerTower.getDamage());
    }

    private void attackCardToBuilding(Card attackerCard, Building targetBuilding) {
        int damage = 0;
        if (attackerCard instanceof TroopsCard) {
            damage = ((TroopsCard) attackerCard).getDamage();
        } else if (attackerCard instanceof Building) {
            damage = ((Building) attackerCard).getDamage();
        }
        targetBuilding.setHp(targetBuilding.getHp() - damage);
    }

    private void attackBuildingToCard(Building attackerBuilding, Card targetCard) {
        if (targetCard instanceof TroopsCard) {
            ((TroopsCard) targetCard).setHp(((TroopsCard) targetCard).getHp() - attackerBuilding.getDamage());
        } else if (targetCard instanceof Building) {
            ((Building) targetCard).setHp(((Building) targetCard).getHp() - attackerBuilding.getDamage());
        }

    }

    private void attackBuildingToBuilding(Building attackerBuilding, Building targetBuilding) {
        targetBuilding.setHp(targetBuilding.getHp() - attackerBuilding.getDamage());

    }

    private ArrayList<Tower> getLivingTowers() {
        return new ArrayList<>();
    }

    private void changeRouteIfBlocked(TroopsCard card, float stepSize) {
        for (Card existingCard : arenaExistingTroops) {
            if (existingCard != card) {
                Point2D currentPosition = new Point2D(card.getCenterPositionX(), card.getCenterPositionY());
                Point2D existingCardPosition = new Point2D(existingCard.getCenterPositionX(), existingCard.getCenterPositionY());
                double distance = currentPosition.distance(existingCardPosition);
                if (distance < stepSize) {
                    System.out.println("Blocking Detected");
                    stepRight(card);
                }
            }
        }
    }

    private void stepRight(Card card) {
        card.setCenterPositionX(card.getCenterPositionX() - unitSize);
    }
    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //------------------------------------------Helper Methods-------------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------

    private ArrayList<Card> findEnemyCardsInRange(float centerX, float centerY, float range) {
        return new ArrayList<>();
    }

    private ArrayList<Tower> findEnemyTowersInRange(float centerX, float centerY, float range) {
        return new ArrayList<>();
    }

    private ArrayList<Building> findEnemyBuildingsInRange(float centerX, float centerY, float range) {
        return new ArrayList<>();
    }

    private int increaseLostHp(int lostHp, int damage) {
        lostHp = lostHp + damage;
        return lostHp;
    }

    private void killCard(Card card) {
        card.setAlive(false);
    }

    private void killTower(Tower tower) {
        tower.setAlive(false);
    }

    private void killBuilding(Building building) {
        building.setAlive(false);
    }

    private void increasePlayersRightBorder(String username) {
        if (username.equals("simpleBot") || username.equals("smartBot")) {
            playerTwoRightBorderY = playerTwoRightBorderY + 70;
        } else {
            playerOneRightBorderY = playerOneRightBorderY - 70;
        }
    }

    private void increasePlayersLeftBorder(String username) {
        if (username.equals("simpleBot") || username.equals("smartBot")) {
            playerTwoLeftBorderY = playerTwoLeftBorderY + 70;
        } else {
            playerOneLeftBorderY = playerOneLeftBorderY - 70;
        }
    }

    private float getRangeSize(Range range) {
        float size = 0;
        if (range.equals(Range.RANGED3))
            size = 3 * minDist;
        else if (range.equals(Range.RANGED5))
            size = 5 * minDist;
        else if (range.equals(Range.RANGED6))
            size = 6 * minDist;
        return size;
    }

    private float getSpeedSize(Speed speed) {
        float size = 1;
        if (speed.equals(Speed.SLOW))
            size = minDist;
        else if (speed.equals(Speed.MEDIUM))
            size = 2;
        else if (speed.equals(Speed.FAST))
            size = 3;
        return (size);
    }

    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------Getters And Setters-----------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public ArrayList<TroopsCard> getArenaExistingTroops() {
        return arenaExistingTroops;
    }

    public void setArenaExistingTroops(ArrayList<TroopsCard> arenaExistingTroops) {
        this.arenaExistingTroops = arenaExistingTroops;
    }

    public ArrayList<Card> getArenaExistingSpellCards() {
        return arenaExistingSpellCards;
    }

    public void setArenaExistingSpellCards(ArrayList<Card> arenaExistingSpellCards) {
        this.arenaExistingSpellCards = arenaExistingSpellCards;
    }

    public ArrayList<Tower> getArenaExistingTowers() {
        return arenaExistingTowers;
    }

    public void setArenaExistingTowers(ArrayList<Tower> arenaExistingTowers) {
        this.arenaExistingTowers = arenaExistingTowers;
    }

    public KingTower getUserKingTower() {
        return userKingTower;
    }

    public void setUserKingTower(KingTower userKingTower) {
        this.userKingTower = userKingTower;
    }

    public QueenTower getUserRightQueenTower() {
        return userRightQueenTower;
    }

    public void setUserRightQueenTower(QueenTower userRightQueenTower) {
        this.userRightQueenTower = userRightQueenTower;
    }

    public QueenTower getUserLeftQueenTower() {
        return userLeftQueenTower;
    }

    public void setUserLeftQueenTower(QueenTower userLeftQueenTower) {
        this.userLeftQueenTower = userLeftQueenTower;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public String getPlayerOneUsername() {
        return playerOneUsername;
    }

    public void setPlayerOneUsername(String playerOneUsername) {
        this.playerOneUsername = playerOneUsername;
    }

    public KingTower getBotKingTower() {
        return botKingTower;
    }

    public void setBotKingTower(KingTower botKingTower) {
        this.botKingTower = botKingTower;
    }

    public QueenTower getBotRightQueenTower() {
        return botRightQueenTower;
    }

    public void setBotRightQueenTower(QueenTower botRightQueenTower) {
        this.botRightQueenTower = botRightQueenTower;
    }

    public QueenTower getBotLeftQueenTower() {
        return botLeftQueenTower;
    }

    public void setBotLeftQueenTower(QueenTower botLeftQueenTower) {
        this.botLeftQueenTower = botLeftQueenTower;
    }

    public String getBotUsername() {
        return botUsername;
    }

    public void setBotUsername(String botUsername) {
        this.botUsername = botUsername;
    }

    public float getBridgeWidth() {
        return bridgeWidth;
    }

    public void setBridgeWidth(float bridgeWidth) {
        this.bridgeWidth = bridgeWidth;
    }

    public float getLeftBridgeX() {
        return leftBridgeX;
    }

    public void setLeftBridgeX(float leftBridgeX) {
        this.leftBridgeX = leftBridgeX;
    }

    public float getBridgesEndY() {
        return bridgesEndY;
    }

    public void setBridgesEndY(float bridgesEndY) {
        this.bridgesEndY = bridgesEndY;
    }

    public float getRightBridgeX() {
        return rightBridgeX;
    }

    public void setRightBridgeX(float rightBridgeX) {
        this.rightBridgeX = rightBridgeX;
    }

    public float getBridgesStartY() {
        return bridgesStartY;
    }

    public void setBridgesStartY(float bridgesStartY) {
        this.bridgesStartY = bridgesStartY;
    }

    public ArrayList<Building> getArenaExistingBuildings() {
        return arenaExistingBuildings;
    }

    public void setArenaExistingBuildings(ArrayList<Building> arenaExistingBuildings) {
        this.arenaExistingBuildings = arenaExistingBuildings;
    }
}
