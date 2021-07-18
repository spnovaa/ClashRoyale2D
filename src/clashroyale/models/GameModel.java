package clashroyale.models;

import clashroyale.models.cardsmodels.buildings.Building;
import clashroyale.models.cardsmodels.troops.Card;
import clashroyale.models.cardsmodels.troops.TroopsCard;
import clashroyale.models.towersmodels.KingTower;
import clashroyale.models.towersmodels.QueenTower;
import clashroyale.models.towersmodels.Tower;

import java.util.ArrayList;

public class GameModel {
    private UserModel userModel;

    private int playerOneCrowns;
    private int playerTwoCrowns;
    private int playerOneLostHP;
    private int playerTwoLostHP;

    private ArrayList<Card> arenaExistingCards;
    private ArrayList<Card> arenaExistingSpellCards;
    private ArrayList<Tower> arenaExistingTowers;
    private KingTower userKingTower;
    private QueenTower userRightQueenTower;
    private QueenTower userLeftQueenTower;
    private int userLevel;
    private String playerOneUsername;

    private KingTower botKingTower;
    private QueenTower botRightQueenTower;
    private QueenTower botLeftQueenTower;
    private String botUsername;

    private float bridgeWidth;

    private float leftBridgeX;
    private float leftBridgeStartY;     //-> bottom
    private float leftBridgeEndY;       //-> top

    private float rightBridgeX;
    private float rightBridgeStartY;     //-> bottom
    private float rightBridgeEndY;       //-> top

    private float playerOneRightBorderY;
    private float playerOneLeftBorderY;

    private float playerTwoRightBorderY;
    private float playerTwoLeftBorderY;

    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //---------------------------------Constructor And Initialization------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------

    public GameModel(UserModel userModel) {
        this.userModel = userModel;
        this.playerOneUsername = userModel.getUsername();
        this.botUsername = "simpleBot";
        arenaExistingCards = new ArrayList<>();
        arenaExistingSpellCards= new ArrayList<>();
        arenaExistingTowers = new ArrayList<>();
        playerOneCrowns = 0;
        playerTwoCrowns = 0;
        playerTwoLostHP = 0;
        playerOneLostHP = 0;
        userLevel = userModel.getLevel();
        initializeBorders();
        initializeUserTowers();
        initializeBotTowers();
        initializeBridges();
    }

    private void initializeBridges() {
        bridgeWidth = 30;

        leftBridgeStartY = 260;
        leftBridgeX = 85;
        leftBridgeEndY = leftBridgeStartY - bridgeWidth;

        rightBridgeStartY = 260;
        rightBridgeX = 275;
        rightBridgeEndY = rightBridgeStartY - bridgeWidth;

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

    }

    private void moveCardToBridge() {

    }

    private void passCardFromBridge(Card cardToMove) {

    }

    private void moveCardToLeftQueenTower(Card cardToMove) {

    }

    private void moveCardToRightQueenTower(Card cardToMove) {

    }

    private void moveCardToKingTower(Card cardToMove) {

    }

    private void attackCardToTower(Card attackerCard, Tower targetTower) {
        int hp = 0;
        if (attackerCard instanceof TroopsCard) {
            hp = targetTower.getHp() - ((TroopsCard) attackerCard).getDamage();
        } else if (attackerCard instanceof Building) {
            hp = targetTower.getHp() - ((Building) attackerCard).getDamage();
        }
        targetTower.setHp(hp);

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
        int damage=0;
        if (attackerCard instanceof TroopsCard){
            damage = ((TroopsCard)attackerCard).getDamage();
        }
       else if (attackerCard instanceof Building){
           damage = ((Building)attackerCard).getDamage();
       }
       targetBuilding.setHp(targetBuilding.getHp() - damage);
    }

    private void attackBuildingToCard(Building attackerBuilding, Card targetCard) {
        if (targetCard instanceof TroopsCard){
            ((TroopsCard)targetCard).setHp( ((TroopsCard)targetCard).getHp() - attackerBuilding.getDamage());
        }
        else if (targetCard instanceof Building){
            ((Building)targetCard).setHp( ((Building)targetCard).getHp() - attackerBuilding.getDamage());
        }

    }

    private void attackBuildingToBuilding(Building attackerBuilding, Building targetBuilding) {
        targetBuilding.setHp(targetBuilding.getHp() - attackerBuilding.getDamage());

    }

    private ArrayList<Tower> getLivingTowers() {
        return new ArrayList<>();
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

    public ArrayList<Card> getArenaExistingCards() {
        return arenaExistingCards;
    }

    public void setArenaExistingCards(ArrayList<Card> arenaExistingCards) {
        this.arenaExistingCards = arenaExistingCards;
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

    public float getLeftBridgeStartY() {
        return leftBridgeStartY;
    }

    public void setLeftBridgeStartY(float leftBridgeStartY) {
        this.leftBridgeStartY = leftBridgeStartY;
    }

    public float getLeftBridgeEndY() {
        return leftBridgeEndY;
    }

    public void setLeftBridgeEndY(float leftBridgeEndY) {
        this.leftBridgeEndY = leftBridgeEndY;
    }

    public float getRightBridgeX() {
        return rightBridgeX;
    }

    public void setRightBridgeX(float rightBridgeX) {
        this.rightBridgeX = rightBridgeX;
    }

    public float getRightBridgeStartY() {
        return rightBridgeStartY;
    }

    public void setRightBridgeStartY(float rightBridgeStartY) {
        this.rightBridgeStartY = rightBridgeStartY;
    }

    public float getRightBridgeEndY() {
        return rightBridgeEndY;
    }

    public void setRightBridgeEndY(float rightBridgeEndY) {
        this.rightBridgeEndY = rightBridgeEndY;
    }

}
