package clashroyale.models;

import clashroyale.models.cardsmodels.buildings.Building;
import clashroyale.models.cardsmodels.buildings.Cannon;
import clashroyale.models.cardsmodels.buildings.InfernoTower;
import clashroyale.models.cardsmodels.spells.Arrows;
import clashroyale.models.cardsmodels.spells.Fireball;
import clashroyale.models.cardsmodels.spells.Rage;
import clashroyale.models.cardsmodels.spells.Spells;
import clashroyale.models.cardsmodels.troops.BabyDragonCard;
import clashroyale.models.cardsmodels.troops.Card;
import clashroyale.models.cardsmodels.troops.TroopsCard;
import clashroyale.models.enums.Range;
import clashroyale.models.enums.Speed;
import clashroyale.models.enums.Target;
import clashroyale.models.game.Robot;
import clashroyale.models.towersmodels.KingTower;
import clashroyale.models.towersmodels.QueenTower;
import clashroyale.models.towersmodels.Tower;
import javafx.application.Platform;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * The type Game model.
 */
public class GameModel {
    private UserModel userModel;

    private int playerLostHP;
    private int botLostHP;
    private int playerCrown;
    private int robotCrown;

    private final float unitSize = 20;

    private ArrayList<TroopsCard> arenaExistingTroops;
    private ArrayList<Spells> arenaExistingSpellCards;
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

    private int elixirFlag;
    private Robot bot;

    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //---------------------------------Constructor And Initialization------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------

    /**
     * Instantiates a new Game model.
     *
     * @param userModel the user model
     * @param bot       the bot
     */
    public GameModel(UserModel userModel, Robot bot) {
        this.userModel = userModel;
        this.bot = bot;
        this.playerOneUsername = userModel.getUsername();

        this.botUsername = bot.getUsername();
        System.out.println(bot.getUsername());

        minDist = 1;
        elixirFlag = 0;

        robotCrown = 0;
        playerCrown = 0;

        arenaExistingTroops = new ArrayList<>();
        arenaExistingSpellCards = new ArrayList<>();
        arenaExistingTowers = new ArrayList<>();
        arenaExistingBuildings = new ArrayList<>();

        playerLostHP = 0;
        botLostHP = 0;

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

    private void initializeBot(Robot bot) {
        this.bot = bot;
    }

    private void initializeUserTowers() {
        userKingTower = new KingTower(userLevel, playerOneUsername, 180, 420, 35, "userKingTower");
        userRightQueenTower = new QueenTower(userLevel, playerOneUsername, 270, 375, 20, "userRightQueenTower");
        userLeftQueenTower = new QueenTower(userLevel, playerOneUsername, 85, 375, 20, "userLeftQueenTower");
        arenaExistingTowers.add(userKingTower);
        arenaExistingTowers.add(userRightQueenTower);
        arenaExistingTowers.add(userLeftQueenTower);
    }

    private void initializeBotTowers() {
        botKingTower = new KingTower(userLevel, botUsername, 180, 75, 35, "botKingTower");
        botRightQueenTower = new QueenTower(userLevel, botUsername, 270, 115, 20, "botRightQueenTower");
        botLeftQueenTower = new QueenTower(userLevel, botUsername, 85, 115, 20, "botLeftQueenTower");
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

    /**
     * Update game model.
     */
    public void updateGameModel() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (TroopsCard card : arenaExistingTroops) {
                    if (card.isAlive()) {
                        boolean isAttacking = attackNearestEnemyInRange(card);
                        if (!isAttacking) repositionCard(card);
                    }
                }

            }
        });
        towersHandling();

        for (Spells card : arenaExistingSpellCards) {
            if (card.isAlive()) {
                card.decreaseTime();
            }
            if (card.getTime() <= 0) {
                card.setAlive(false);
                killCard(card);
            }

        }


        for (Building building : arenaExistingBuildings) {
            if (building.isAlive()) {
                Point2D cardPosition = new Point2D(building.getCenterPositionX(), building.getCenterPositionY());
                if (building instanceof InfernoTower) {
                    for (TroopsCard troopsCard : arenaExistingTroops) {
                        if (!troopsCard.getRelatedUser().equals(building.getRelatedUser())) {
                            Point2D troopCardPosition = new Point2D(troopsCard.getCenterPositionX(), troopsCard.getCenterPositionY());
                            float distance = (float) cardPosition.distance(troopCardPosition);
//                            System.out.println("distance " + distance);
                            if (distance <= building.getRange() * 10) {
                                troopsCard.setHp(troopsCard.getHp() - building.getDamage());
                                if (troopsCard.getHp() <= 0) {
                                    killCard(troopsCard);
                                }
                            }
                        }
                    }
                    for (Tower tower : arenaExistingTowers) {
                        if (!tower.getRelatedUser().equals(building.getRelatedUser())) {
                            Point2D troopCardPosition = new Point2D(tower.getCenterPositionX(), tower.getCenterPositionY());
                            float distance = (float) cardPosition.distance(troopCardPosition);
//                            System.out.println("distance " + distance);
                            if (distance <= building.getRange() * 10) {
                                tower.setHp(tower.getHp() - building.getDamage());
                                if (tower.getHp() <= 0) {
                                    killTower(tower);
                                }
                            }
                        }
                    }
                    int increaseDamage = (((InfernoTower) building).getMaxDamage() - ((InfernoTower) building).getMinDamage()) / building.getFixLifeTime();
                    building.setDamage(building.getDamage() + increaseDamage);
                } else if (building instanceof Cannon) {
                    for (TroopsCard troopsCard : arenaExistingTroops) {
                        if (!(troopsCard.getTitle().equals("babyDragon"))) {
                            if (!troopsCard.getRelatedUser().equals(building.getRelatedUser())) {
                                Point2D troopCardPosition = new Point2D(troopsCard.getCenterPositionX(), troopsCard.getCenterPositionY());
                                float distance = (float) cardPosition.distance(troopCardPosition);
//                                System.out.println("distance " + distance);
                                if (distance <= building.getRange() * 10) {
                                    troopsCard.setHp(troopsCard.getHp() - building.getDamage());
                                    if (troopsCard.getHp() <= 0) {
                                        killCard(troopsCard);
                                    }
                                }
                            }
                        }
                    }
                    for (Tower tower : arenaExistingTowers) {
                        if (!tower.getRelatedUser().equals(building.getRelatedUser())) {
                            Point2D troopCardPosition = new Point2D(tower.getCenterPositionX(), tower.getCenterPositionY());
                            float distance = (float) cardPosition.distance(troopCardPosition);
//                            System.out.println("distance " + distance);
                            if (distance <= building.getRange() * 10) {
                                tower.setHp(tower.getHp() - building.getDamage());
                                if (tower.getHp() <= 0) {
                                    killTower(tower);
                                }
                            }
                        }
                    }
                }
            }
            building.decreaseLifeTime();
            if (building.getLifeTime() <= 0) {
                building.setAlive(false);
            }
        }
    }

    private void towersHandling() {
//        Point2D userRightQueen = new Point2D(userRightQueenTower.getCenterPositionX(), userRightQueenTower.getCenterPositionY());
//        Point2D userLeftQueen = new Point2D(userLeftQueenTower.getCenterPositionX(), userLeftQueenTower.getCenterPositionY());
//        Point2D userKing = new Point2D(userKingTower.getCenterPositionX(), userKingTower.getCenterPositionY());
//        Point2D botRightQueen = new Point2D(botRightQueenTower.getCenterPositionX(), botRightQueenTower.getCenterPositionY());
//        Point2D botLeftQueen = new Point2D(botLeftQueenTower.getCenterPositionX(), botLeftQueenTower.getCenterPositionY());
//        Point2D botKing = new Point2D(botKingTower.getCenterPositionX(), botKingTower.getCenterPositionY());
        towerAttackHandling(userRightQueenTower);
        towerAttackHandling(userLeftQueenTower);
        towerAttackHandling(userKingTower);

        towerAttackHandling(botRightQueenTower);
        towerAttackHandling(botLeftQueenTower);
        towerAttackHandling(botKingTower);
    }

    private void repositionCard(TroopsCard card) {
        float cardX = card.getCenterPositionX();
        float cardY = card.getCenterPositionY();
        boolean isBot = card.getRelatedUser().equals("simpleBot") || card.getRelatedUser().equals("smartBot");
        boolean canFly = card.getTitle().equals("babyDragon");
        boolean isDown = cardY > bridgesStartY;
        boolean isRight = cardX >= fieldCenterX;
        boolean isUp = cardY < bridgesEndY;

        Point2D invaderPosition = findInvader(card);
        if (invaderPosition != null) {
            stepToTarget(card, (float) invaderPosition.getX(), (float) invaderPosition.getY(), true);
        } else {
            if (isDown && !canFly) {                                                    //card is in user's field and moves on the ground
                if (isRight) {
                    if (!isBot)
                        moveCardToStartOfRightBridge(card);                     //user card is in right down quarter
                    else if (userRightQueenTower.isAlive()) moveCardToRightUserQueen(card);
                    else moveCardToUserKing(card);
                } else {                                                                //card is in left half
                    if (!isBot) moveCardToStartOfLeftBridge(card);
                    else if (userLeftQueenTower.isAlive()) moveCardToLeftUserQueen(card);
                    else moveCardToUserKing(card);
                }
            } else if (isUp && !canFly) {                                                //card is in enemy's field or moves on air
                if (isRight) {                                                          //card is in the right side
                    if (isBot) moveCardToEndOfRightBridge(card);
                    else if (botRightQueenTower.isAlive()) moveCardToRightQueenTower(card);
                    else moveCardToKingTower(card);
                } else {
                    if (isBot) moveCardToEndOfLeftBridge(card);
                    else if (botLeftQueenTower.isAlive()) moveCardToLeftQueenTower(card);
                    else moveCardToKingTower(card);
                }
            } else {
                if (isBot && isRight && userRightQueenTower.isAlive()) moveCardToRightUserQueen(card);
                else if (isBot && isRight && !userRightQueenTower.isAlive()) moveCardToUserKing(card);
                else if (isBot && !isRight && userLeftQueenTower.isAlive()) moveCardToLeftUserQueen(card);
                else if (isBot && !isRight && !userLeftQueenTower.isAlive()) moveCardToUserKing(card);

                else if (!isBot && isRight && botRightQueenTower.isAlive()) moveCardToRightQueenTower(card);
                else if (!isBot && isRight && !botRightQueenTower.isAlive()) moveCardToKingTower(card);
                else if (!isBot && !isRight && botLeftQueenTower.isAlive()) moveCardToLeftQueenTower(card);
                else if (!isBot && !isRight && !botLeftQueenTower.isAlive()) moveCardToKingTower(card);
            }
        }
    }

    private Point2D findInvader(TroopsCard card) {
        Point2D invaderPosition = null;
        if (!card.getTarget().equals(Target.BUILDINGS)) {
            Point2D cardPosition = new Point2D(card.getCenterPositionX(), card.getCenterPositionY());
            for (Card existing : arenaExistingTroops) {
                boolean canAttack = existing.isAlive() && !(existing.getTitle().equals("babyDragon") &&
                        (card.getTarget().equals(Target.GROUND) || card.getTarget().equals(Target.BUILDINGS)));
                if (!existing.getRelatedUser().equals(card.getRelatedUser()) && canAttack) {
                    Point2D pos = new Point2D(existing.getCenterPositionX(), existing.getCenterPositionY());
                    float dist = (float) pos.distance(cardPosition);
                    if (dist < 70.0)
                        invaderPosition = pos;
                }
            }
        }
        return invaderPosition;
    }

    /**
     * Spell action.
     *
     * @param temp the temp
     */
    public void spellAction(Card temp) {
        String title = temp.getTitle();
        Spells spell;
        if (title.equalsIgnoreCase("rage"))
            spell = (Rage) temp;
        else if (title.equalsIgnoreCase("arrows"))
            spell = (Arrows) temp;
        else
            spell = (Fireball) temp;

        Point2D cardPosition = new Point2D(temp.getCenterPositionX(), temp.getCenterPositionY());

        for (TroopsCard troopsCard : arenaExistingTroops) {
            Point2D troopCardPosition = new Point2D(troopsCard.getCenterPositionX(), troopsCard.getCenterPositionY());
            float distance = (float) cardPosition.distance(troopCardPosition);
//            System.out.println("distance " + distance);
//            System.out.println("spell radius : " + spell.getRadious() * 10);
            if (distance <= spell.getRadious() * 10) {
//                System.out.println("Troop " + troopsCard.getTitle() + " is detected in " + spell.getTitle() + " range");
                if (spell instanceof Fireball) {
                    if (!troopsCard.getRelatedUser().equals(spell.getRelatedUser())) {
                        troopsCard.setHp(troopsCard.getHp() - ((Fireball) spell).getAreaDamage());
                        if (troopsCard.getHp() <= 0) {
                            killCard(troopsCard);
                        }
                    }
                } else if (spell instanceof Rage) {
//                    System.out.println(troopsCard.getRelatedUser() + " and " + spell.getRelatedUser());
                    if (troopsCard.getRelatedUser().equals(spell.getRelatedUser())) {
                        troopsCard.setDamage(troopsCard.getDamage() * 1.4);
                        troopsCard.setHitSpeed(troopsCard.getHitSpeed() * 1.4);
                        //speed
                        if (troopsCard.getSpeed().equals(Speed.SLOW)) {
                            troopsCard.setSpeed(Speed.SLOW_AMPLIFIED);
//                            System.out.println("dddddddd");
                        } else if (troopsCard.getSpeed().equals(Speed.MEDIUM)) {
                            troopsCard.setSpeed(Speed.MEDIUM_AMPLIFIED);
//                            System.out.println("ddddddd");
                        } else if (troopsCard.getSpeed().equals(Speed.FAST)) {
                            troopsCard.setSpeed(Speed.FAST_AMPLIFIED);
                        }
                    }
                } else {
                    if (!troopsCard.getRelatedUser().equals(spell.getRelatedUser())) {
                        killCard(troopsCard);
                    }
                }
            }
        }
        for (Tower tower : arenaExistingTowers) {
            Point2D towerPosition = new Point2D(tower.getCenterPositionX(), tower.getCenterPositionY());
            float distance1 = (float) cardPosition.distance(towerPosition);
            if (distance1 <= spell.getRadious() * 10) {
                Point2D troopCardPosition = new Point2D(tower.getCenterPositionX(), tower.getCenterPositionY());
                float distance = (float) cardPosition.distance(troopCardPosition);
                if (distance <= spell.getRadious() * 10) {
                    if (spell instanceof Fireball) {
                        if (!tower.getRelatedUser().equals(spell.getRelatedUser())) {
                            tower.setHp(tower.getHp() - ((Fireball) spell).getAreaDamage());
                            if (tower.getHp() <= 0) {
                                killTower(tower);
                                if (tower.getRelatedUser().equals(spell.getRelatedUser())){
                                    if (tower instanceof KingTower)
                                        robotCrown=3;
                                    else if (tower instanceof QueenTower)
                                        robotCrown++;

                                }else {
                                    if (tower instanceof KingTower)
                                        playerCrown=3;
                                    else if (tower instanceof QueenTower)
                                        playerCrown++;

                                }
                            }
                        }
                    } else if (spell instanceof Rage) {
                        if (tower.getRelatedUser().equals(spell.getRelatedUser())) {
                            tower.setDamage(tower.getDamage() * 1.4);
                            tower.setHitSpeed(tower.getHitSpeed() * 1.4);

                        }
                    } else {
                        if (!tower.getRelatedUser().equals(spell.getRelatedUser())) {
                            killTower(tower);
                        }
                    }
                }
            }
        }
        for (Building building : arenaExistingBuildings) {
            Point2D buildingPosition = new Point2D(building.getCenterPositionX(), building.getCenterPositionY());
            float distance2 = (float) cardPosition.distance(buildingPosition);
            if (distance2 <= spell.getRadious() * 10) {
                Point2D towerPosition = new Point2D(building.getCenterPositionX(), building.getCenterPositionY());
                float distance1 = (float) cardPosition.distance(towerPosition);
                if (distance1 <= spell.getRadious() * 10) {
                    Point2D troopCardPosition = new Point2D(building.getCenterPositionX(), building.getCenterPositionY());
                    float distance = (float) cardPosition.distance(troopCardPosition);
                    if (distance <= spell.getRadious() * 10) {
                        if (spell instanceof Fireball) {
                            if (!building.getRelatedUser().equals(spell.getRelatedUser())) {
                                building.setHp(building.getHp() - ((Fireball) spell).getAreaDamage());
                                if (building.getHp() <= 0) {
                                    killBuilding(building);
                                }
                            }
                        } else if (spell instanceof Rage) {
                            if (building.getRelatedUser().equals(spell.getRelatedUser())) {
                                building.setDamage(building.getDamage() * 1.4);
                                building.setHitSpeed(building.getHitSpeed() * 1.4);
                                //speed
                            }
//                             else if (spell instanceof Arrows){
//                                 if (!building.getRelatedUser().equals(spell)){
//                                     killBuilding(building);
//                                 }
//                             }
                        }
                    }
                }
            }
        }

    }

    private void moveCardToStartOfRightBridge(TroopsCard card) {
        stepToTarget(card, rightBridgeX, bridgesStartY, true);
//        System.out.println("Moving Card To Right Bridge");
    }

    private void moveCardToStartOfLeftBridge(TroopsCard card) {
        stepToTarget(card, leftBridgeX, bridgesStartY, true);
//        System.out.println("Moving Card To Left Bridge");
    }

    private void passUserCardFromRightBridge(TroopsCard cardToMove) {
        stepToTarget(cardToMove, rightBridgeX, bridgesEndY, true);
//        System.out.println("Passing Card from Right Bridge");
    }

    private void passUserCardFromLeftBridge(TroopsCard cardToMove) {
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

    private void moveCardToRightUserQueen(TroopsCard card) {
        stepToTarget(card, userRightQueenTower.getCenterPositionX(), userRightQueenTower.getCenterPositionY(), false);
    }

    private void moveCardToUserKing(TroopsCard card) {
        stepToTarget(card, userKingTower.getCenterPositionX(), userKingTower.getCenterPositionY(), false);
    }

    private void moveCardToLeftUserQueen(TroopsCard card) {
        stepToTarget(card, userLeftQueenTower.getCenterPositionX(), userLeftQueenTower.getCenterPositionY(), false);
    }

    private void moveCardToEndOfRightBridge(TroopsCard card) {
        stepToTarget(card, rightBridgeX, bridgesEndY, true);
    }

    private void moveCardToEndOfLeftBridge(TroopsCard card) {
        stepToTarget(card, leftBridgeX, bridgesEndY, true);
    }

    private void passBotCardFromRightBridge(TroopsCard card) {
        stepToTarget(card, rightBridgeX, bridgesStartY, true);
    }

    private void passBotCardFromLeftBridge(TroopsCard card) {
        stepToTarget(card, leftBridgeX, bridgesStartY, true);
    }

    private void stepToTarget(TroopsCard card, float targetX, float targetY, boolean isPassable) {
        float currentX = card.getCenterPositionX();
        float currentY = card.getCenterPositionY();
        float rangeSize = getRangeSize(card.getRangeType());
        float speedSize = getSpeedSize(card.getSpeed());

        Point2D current = new Point2D(currentX, currentY);
        Point2D target = new Point2D(targetX, targetY);
        boolean shouldMove = (isPassable && current.distance(target) > minDist) ||
                (!isPassable && current.distance(target) > getRangeSize(card.getRangeType()));
        if (shouldMove) {
            //if card's range is farther than targets center
            double distance = current.distance(target);
            float distanceX = targetX - currentX;
            float distanceY = targetY - currentY;
            double xRatio = distanceX / distance;
            double yRatio = distanceY / distance;

            float stepX = (float) (speedSize * xRatio);
            float stepY = (float) (speedSize * yRatio);
            changeRouteIfBlocked(card, speedSize, new Point2D(stepX, stepY));

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
                card.setCenterPositionY(targetY + 2);
            }

        }
    }

    private boolean attackNearestEnemyInRange(Card card) {
        boolean isAttacking = false;
        float distanceToTower = 10000;
        float distanceToCard = 10000;

        Card enemyCard = findNearestEnemyCardInRange(card);
        Tower enemyTower = findNearestEnemyTowerInRange(card);
        Point2D cardPosition = new Point2D(card.getCenterPositionX(), card.getCenterPositionY());

        if (enemyCard != null && enemyCard.isAlive()) {
            Point2D enemyCardPosition = new Point2D(enemyCard.getCenterPositionX(), enemyCard.getCenterPositionY());
            distanceToCard = (float) enemyCardPosition.distance(cardPosition);
            isAttacking = true;
        }
        if (enemyTower != null && enemyTower.isAlive()) {
            Point2D enemyTowerPosition = new Point2D(enemyTower.getCenterPositionX(), enemyTower.getCenterPositionY());
            distanceToTower = (float) enemyTowerPosition.distance(cardPosition);
            isAttacking = true;
        }


        if (distanceToTower < distanceToCard && distanceToTower <= getRangeSize(((TroopsCard) card).getRangeType())
                && enemyTower != null) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    attackCardToTower(card, enemyTower);
                }
            });
        } else if (distanceToCard <= getRangeSize(((TroopsCard) card).getRangeType())) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    attackCardToCard(card, enemyCard);
                }
            });
        }
//        System.out.println("Is Attacking : "+ isAttacking);
        return isAttacking;
    }


    private void towerAttackHandling(Tower tower) {
        Card enemyCard = findNearestTowerEnemy(tower);

        if (tower.getTarget() == null) {
            tower.setTarget(enemyCard);
        }
        attackTowerToCard(tower, enemyCard);
        if (enemyCard instanceof TroopsCard) {
            if (((TroopsCard) enemyCard).getHp() < 0) {
                killCard(enemyCard);
                tower.setTarget(null);
            }
        }
    }

    private Card findNearestTowerEnemy(Tower tower) {
        Card nearest = null;
        float leastDist = 8000f;

        Point2D towerPosition = new Point2D(tower.getCenterPositionX(), tower.getCenterPositionY());
        for (Card card : arenaExistingTroops) {
            Point2D cardPosition = new Point2D(card.getCenterPositionX(), card.getCenterPositionY());
            if (card.isAlive() && !card.getRelatedUser().equals(tower.getRelatedUser())) {
                float distance = (float) towerPosition.distance(cardPosition);
                if (distance < leastDist && distance <= tower.getRange() * 10) {
                    nearest = card;
                    leastDist = distance;
                }
            }
        }
        return nearest;
    }

    private Tower findNearestEnemyTowerInRange(Card card) {
        if (!(card.getRelatedUser().equals("simpleBot") || card.getRelatedUser().equals("smartBot"))) {
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
        } else {
            Point2D cardPosition = new Point2D(card.getCenterPositionX(), card.getCenterPositionY());
            Point2D userRightQueenPosition = new Point2D(userRightQueenTower.getCenterPositionX(), userRightQueenTower.getCenterPositionY());
            Point2D userLeftQueenPosition = new Point2D(userLeftQueenTower.getCenterPositionX(), userLeftQueenTower.getCenterPositionY());
            Point2D userKingPosition = new Point2D(userKingTower.getCenterPositionX(), userKingTower.getCenterPositionY());

            float userRightQueenDistance = (float) cardPosition.distance(userRightQueenPosition);
            float userLeftQueenDistance = (float) cardPosition.distance(userLeftQueenPosition);
            float userKingDistance = (float) cardPosition.distance(userKingPosition);

            Float[] distances = {userKingDistance, userLeftQueenDistance, userRightQueenDistance};
            float min = Collections.min(Arrays.asList(distances));

            if (min <= getRangeSize(((TroopsCard) card).getRangeType())) {
                if (min == userKingDistance)
                    return userKingTower;
                if (min == userRightQueenDistance)
                    return userRightQueenTower;
                if (min == userLeftQueenDistance)
                    return userLeftQueenTower;
            }
        }

        //if no enemy tower found in range, returns null
        return null;
    }

    private Card findNearestEnemyCardInRange(Card card) {
        Card nearestEnemy = null;
        float leastDistance;

        if (card instanceof TroopsCard)
            leastDistance = getRangeSize(((TroopsCard) card).getRangeType());
        else if (card instanceof Building)
            leastDistance = (float) ((Building) card).getRange();
        else
            leastDistance = 10000;
        boolean isGiant = card.getTitle().equals("giant");
        for (TroopsCard troopsCard : arenaExistingTroops) {
            if (!troopsCard.getRelatedUser().equals(card.getRelatedUser()) && !isGiant) {
                float distance = calcDistance(card, troopsCard);
                if (distance < leastDistance && troopsCard.isAlive()) {
//                    System.out.println(troopsCard.getTitle() +" for "+ troopsCard.getRelatedUser());
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
        System.out.println(attackerCard.getTitle() + " is Attacking " + targetTower.getRelatedUser() + " " + targetTower.getTitle());
        double hp = 0;
        if (attackerCard instanceof TroopsCard) {
//            System.out.println("troop damage :" + ((TroopsCard) attackerCard).getDamage());
//            System.out.println("TowerHp : " + targetTower.getHp());
            hp = targetTower.getHp() - ((TroopsCard) attackerCard).getDamage();
        } else if (attackerCard instanceof Building) {
            hp = targetTower.getHp() - ((Building) attackerCard).getDamage();
        }
//        System.out.println(" Tower Hp : " + hp);
        if (hp >= 0)
            targetTower.setHp(hp);
        else {
            killTower(targetTower);
//            targetTower.setHp(0);
//            targetTower.setAlive(false);
//            System.out.println("Tower " + targetTower.getUuid() + " is now dead");
        }

    }

    private void attackTowerToCard(Tower attackerTower, Card targetCard) {
        if (attackerTower.isAlive()) {
            if (targetCard instanceof TroopsCard) {
                ((TroopsCard) targetCard).setHp(((TroopsCard) targetCard).getHp() - attackerTower.getDamage());
//                System.out.println(attackerTower.getRelatedUser()+" is attacking " + targetCard.getTitle()+ " hpe : "+
//                        ((TroopsCard) targetCard).getHp());
                if (((TroopsCard) targetCard).getHp() < 0) {
                    killCard(targetCard);
                    attackerTower.setTarget(null);
                }
            } else if (targetCard instanceof Building) {
                ((Building) targetCard).setHp(((Building) targetCard).getHp() - attackerTower.getDamage());
                if (((Building) targetCard).getHp() < 0) {
                    killCard(targetCard);
                    attackerTower.setTarget(null);
                }
            }
        }
    }

    private void attackCardToCard(Card attackerCard, Card targetCard) {
        if (targetCard instanceof TroopsCard) {
            boolean isAttackAllowed = !targetCard.getTitle().equals("babyDragon") ||
                    ((TroopsCard) attackerCard).getTarget().equals(Target.AIR) ||
                    ((TroopsCard) attackerCard).getTarget().equals(Target.AIR_GROUND);
            if (attackerCard instanceof TroopsCard && isAttackAllowed) {
                System.out.println(((TroopsCard) targetCard).getHp() + " and " + ((TroopsCard) attackerCard).getHp());
                ((TroopsCard) targetCard).setHp(((TroopsCard) targetCard).getHp() - ((TroopsCard) attackerCard).getDamage());
                if (((TroopsCard) targetCard).getHp() < 0) killCard(targetCard);
            } else if (attackerCard instanceof Building) {
                ((TroopsCard) targetCard).setHp(((TroopsCard) targetCard).getHp() - ((Building) attackerCard).getDamage());
                if (((TroopsCard) targetCard).getHp() < 0) killCard(targetCard);
            }
        } else if (targetCard instanceof Building) {
            if (attackerCard instanceof TroopsCard) {
                ((Building) targetCard).setHp(((Building) targetCard).getHp() - ((TroopsCard) attackerCard).getDamage());
                if (((Building) targetCard).getHp() < 0) killCard(targetCard);
            } else if (attackerCard instanceof Building) {
                ((Building) targetCard).setHp(((Building) targetCard).getHp() - ((Building) attackerCard).getDamage());
                if (((Building) targetCard).getHp() < 0) killCard(targetCard);
            }
        }
    }

    private void attackTowerToTower(Tower attackerTower, Tower targetTower) {
        targetTower.setHp(targetTower.getHp() - attackerTower.getDamage());
    }

    private void attackCardToBuilding(Card attackerCard, Building targetBuilding) {
        double damage = 0;
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

    private void changeRouteIfBlocked(TroopsCard card, float stepSize, Point2D cardVector) {
        for (Card existingCard : arenaExistingTroops) {
            if (existingCard != card && existingCard.isAlive()) {
                Point2D currentPosition = new Point2D(card.getCenterPositionX(), card.getCenterPositionY());
                Point2D existingCardPosition = new Point2D(existingCard.getCenterPositionX(), existingCard.getCenterPositionY());
                double distance = currentPosition.distance(existingCardPosition);
                if (distance < stepSize + unitSize) {
//                    System.out.println("Blocking Detected");
                    manageBlocking(card, existingCard, cardVector);
                } else card.setBeingBlocked(false);
            }
        }
    }

    private void manageBlocking(Card card, Card otherCard, Point2D vector) {

        if ((!card.isBeingBlocked() && !otherCard.isBeingBlocked()) || (card.isBeingBlocked() && !otherCard.isBeingBlocked())) {
            card.setBeingBlocked(true);
            float newStepX = (float) (-1 * vector.getY());
            float newStepY = (float) vector.getX();

            card.setCenterPositionX(card.getCenterPositionX() + newStepX);
            card.setCenterPositionY(card.getCenterPositionY() + newStepY);
        } else if (!card.isBeingBlocked() && otherCard.isBeingBlocked()) {
            float newStepX = (float) (-1 * vector.getY());
            float newStepY = (float) vector.getX();

            otherCard.setCenterPositionX(otherCard.getCenterPositionX() + newStepX);
            otherCard.setCenterPositionY(otherCard.getCenterPositionY() + newStepY);
        }

//        card.setCenterPositionX(card.getCenterPositionX() - unitSize);
    }


    /**
     * Update elixirs.
     */
    public void updateElixirs() {
        elixirFlag++;
        if (elixirFlag % 30 == 0) {
            int userElixir = userModel.getElixirCount();
            int botElixir = bot.getElixirCount();
            if (userElixir < 10) {
                userElixir++;
                userModel.setElixirCount(userElixir);
            }
            if (botElixir < 10) {
                botElixir++;
                bot.setElixirCount(botElixir);
            }
        }
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

    private synchronized void killTower(Tower tower) {
        if (tower.isAlive()) {
            tower.setAlive(false);
            String user = tower.getRelatedUser();
            System.out.println(tower.getRelatedUser() + " lost " + tower.getTitle());
            if (user.equals("simpleBot") || user.equals("smartBot")) {
                if (tower.getTitle().equals("botKingTower"))
                    playerCrown = 3;
                else if (tower.getTitle().equals("botRightQueenTower") || tower.getTitle().equals("botLeftQueenTower"))
                    playerCrown++;
            } else {
                if (tower.getTitle().equals("userKingTower"))
                    robotCrown = 3;
                else if (tower.getTitle().equals("userRightQueenTower") || tower.getTitle().equals("userLeftQueenTower"))
                    robotCrown++;
            }
        }
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
        float size = 10;
        if (range.equals(Range.RANGED3))
            size = 30 * minDist;
        else if (range.equals(Range.RANGED5))
            size = 50 * minDist;
        else if (range.equals(Range.RANGED6))
            size = 60 * minDist;
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
        else if (speed.equals(Speed.SLOW_AMPLIFIED))
            size = (float) (minDist * 1.4);

        else if (speed.equals(Speed.MEDIUM_AMPLIFIED))
            size = (float) (2 * 1.4);
        else if (speed.equals(Speed.FAST_AMPLIFIED))
            size = (float) (3 * 1.4);

        return (size);
    }

    /**
     * Gets game bot.
     *
     * @return the game bot
     */
    public Robot getGameBot() {
        return bot;
    }

    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------Getters And Setters-----------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------

    /**
     * Gets player crown.
     *
     * @return the player crown
     */
    public int getPlayerCrown() {
        return playerCrown;
    }

    /**
     * Gets robot crown.
     *
     * @return the robot crown
     */
    public int getRobotCrown() {
        return robotCrown;
    }

    /**
     * Gets user model.
     *
     * @return the user model
     */
    public UserModel getUserModel() {
        return userModel;
    }

    /**
     * Sets user model.
     *
     * @param userModel the user model
     */
    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    /**
     * Gets arena existing troops.
     *
     * @return the arena existing troops
     */
    public ArrayList<TroopsCard> getArenaExistingTroops() {
        return arenaExistingTroops;
    }

    /**
     * Sets arena existing troops.
     *
     * @param arenaExistingTroops the arena existing troops
     */
    public void setArenaExistingTroops(ArrayList<TroopsCard> arenaExistingTroops) {
        this.arenaExistingTroops = arenaExistingTroops;
    }

    /**
     * Gets arena existing spell cards.
     *
     * @return the arena existing spell cards
     */
    public ArrayList<Spells> getArenaExistingSpellCards() {
        return arenaExistingSpellCards;
    }

    /**
     * Sets arena existing spell cards.
     *
     * @param arenaExistingSpellCards the arena existing spell cards
     */
    public void setArenaExistingSpellCards(ArrayList<Spells> arenaExistingSpellCards) {
        this.arenaExistingSpellCards = arenaExistingSpellCards;
    }

    /**
     * Gets arena existing towers.
     *
     * @return the arena existing towers
     */
    public ArrayList<Tower> getArenaExistingTowers() {
        return arenaExistingTowers;
    }

    /**
     * Sets arena existing towers.
     *
     * @param arenaExistingTowers the arena existing towers
     */
    public void setArenaExistingTowers(ArrayList<Tower> arenaExistingTowers) {
        this.arenaExistingTowers = arenaExistingTowers;
    }

    /**
     * Gets user king tower.
     *
     * @return the user king tower
     */
    public KingTower getUserKingTower() {
        return userKingTower;
    }

    /**
     * Sets user king tower.
     *
     * @param userKingTower the user king tower
     */
    public void setUserKingTower(KingTower userKingTower) {
        this.userKingTower = userKingTower;
    }

    /**
     * Gets user right queen tower.
     *
     * @return the user right queen tower
     */
    public QueenTower getUserRightQueenTower() {
        return userRightQueenTower;
    }

    /**
     * Sets user right queen tower.
     *
     * @param userRightQueenTower the user right queen tower
     */
    public void setUserRightQueenTower(QueenTower userRightQueenTower) {
        this.userRightQueenTower = userRightQueenTower;
    }

    /**
     * Gets user left queen tower.
     *
     * @return the user left queen tower
     */
    public QueenTower getUserLeftQueenTower() {
        return userLeftQueenTower;
    }

    /**
     * Sets user left queen tower.
     *
     * @param userLeftQueenTower the user left queen tower
     */
    public void setUserLeftQueenTower(QueenTower userLeftQueenTower) {
        this.userLeftQueenTower = userLeftQueenTower;
    }

    /**
     * Gets user level.
     *
     * @return the user level
     */
    public int getUserLevel() {
        return userLevel;
    }

    /**
     * Sets user level.
     *
     * @param userLevel the user level
     */
    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    /**
     * Gets player one username.
     *
     * @return the player one username
     */
    public String getPlayerOneUsername() {
        return playerOneUsername;
    }

    /**
     * Sets player one username.
     *
     * @param playerOneUsername the player one username
     */
    public void setPlayerOneUsername(String playerOneUsername) {
        this.playerOneUsername = playerOneUsername;
    }

    /**
     * Gets bot king tower.
     *
     * @return the bot king tower
     */
    public KingTower getBotKingTower() {
        return botKingTower;
    }

    /**
     * Sets bot king tower.
     *
     * @param botKingTower the bot king tower
     */
    public void setBotKingTower(KingTower botKingTower) {
        this.botKingTower = botKingTower;
    }

    /**
     * Gets bot right queen tower.
     *
     * @return the bot right queen tower
     */
    public QueenTower getBotRightQueenTower() {
        return botRightQueenTower;
    }

    /**
     * Sets bot right queen tower.
     *
     * @param botRightQueenTower the bot right queen tower
     */
    public void setBotRightQueenTower(QueenTower botRightQueenTower) {
        this.botRightQueenTower = botRightQueenTower;
    }

    /**
     * Gets bot left queen tower.
     *
     * @return the bot left queen tower
     */
    public QueenTower getBotLeftQueenTower() {
        return botLeftQueenTower;
    }

    /**
     * Sets bot left queen tower.
     *
     * @param botLeftQueenTower the bot left queen tower
     */
    public void setBotLeftQueenTower(QueenTower botLeftQueenTower) {
        this.botLeftQueenTower = botLeftQueenTower;
    }

    /**
     * Gets bot username.
     *
     * @return the bot username
     */
    public String getBotUsername() {
        return botUsername;
    }

    /**
     * Sets bot username.
     *
     * @param botUsername the bot username
     */
    public void setBotUsername(String botUsername) {
        this.botUsername = botUsername;
    }

    /**
     * Gets bridge width.
     *
     * @return the bridge width
     */
    public float getBridgeWidth() {
        return bridgeWidth;
    }

    /**
     * Sets bridge width.
     *
     * @param bridgeWidth the bridge width
     */
    public void setBridgeWidth(float bridgeWidth) {
        this.bridgeWidth = bridgeWidth;
    }

    /**
     * Gets left bridge x.
     *
     * @return the left bridge x
     */
    public float getLeftBridgeX() {
        return leftBridgeX;
    }

    /**
     * Sets left bridge x.
     *
     * @param leftBridgeX the left bridge x
     */
    public void setLeftBridgeX(float leftBridgeX) {
        this.leftBridgeX = leftBridgeX;
    }

    /**
     * Gets bridges end y.
     *
     * @return the bridges end y
     */
    public float getBridgesEndY() {
        return bridgesEndY;
    }

    /**
     * Sets bridges end y.
     *
     * @param bridgesEndY the bridges end y
     */
    public void setBridgesEndY(float bridgesEndY) {
        this.bridgesEndY = bridgesEndY;
    }

    /**
     * Gets right bridge x.
     *
     * @return the right bridge x
     */
    public float getRightBridgeX() {
        return rightBridgeX;
    }

    /**
     * Sets right bridge x.
     *
     * @param rightBridgeX the right bridge x
     */
    public void setRightBridgeX(float rightBridgeX) {
        this.rightBridgeX = rightBridgeX;
    }

    /**
     * Gets bridges start y.
     *
     * @return the bridges start y
     */
    public float getBridgesStartY() {
        return bridgesStartY;
    }

    /**
     * Sets bridges start y.
     *
     * @param bridgesStartY the bridges start y
     */
    public void setBridgesStartY(float bridgesStartY) {
        this.bridgesStartY = bridgesStartY;
    }

    /**
     * Gets arena existing buildings.
     *
     * @return the arena existing buildings
     */
    public ArrayList<Building> getArenaExistingBuildings() {
        return arenaExistingBuildings;
    }

    /**
     * Sets arena existing buildings.
     *
     * @param arenaExistingBuildings the arena existing buildings
     */
    public void setArenaExistingBuildings(ArrayList<Building> arenaExistingBuildings) {
        this.arenaExistingBuildings = arenaExistingBuildings;
    }


    /**
     * Gets arena towers.
     *
     * @return the arena towers
     */
    public ArrayList<Tower> getArenaTowers() {
        return arenaExistingTowers;
    }

    /**
     * Gets bot.
     *
     * @return the bot
     */
    public Robot getBot() {
        return bot;
    }

    /**
     * Gets player lost hp.
     *
     * @return the player lost hp
     */
    public int getPlayerLostHP() {
        return playerLostHP;
    }

    /**
     * Sets player lost hp.
     *
     * @param playerLostHP the player lost hp
     */
    public void setPlayerLostHP(int playerLostHP) {
        this.playerLostHP = playerLostHP;
    }

    /**
     * Gets bot lost hp.
     *
     * @return the bot lost hp
     */
    public int getBotLostHP() {
        return botLostHP;
    }

    /**
     * Sets bot lost hp.
     *
     * @param botLostHP the bot lost hp
     */
    public void setBotLostHP(int botLostHP) {
        this.botLostHP = botLostHP;
    }

    /**
     * Sets player crown.
     *
     * @param playerCrown the player crown
     */
    public void setPlayerCrown(int playerCrown) {
        this.playerCrown = playerCrown;
    }

    /**
     * Sets robot crown.
     *
     * @param robotCrown the robot crown
     */
    public void setRobotCrown(int robotCrown) {
        this.robotCrown = robotCrown;
    }

    /**
     * Gets unit size.
     *
     * @return the unit size
     */
    public float getUnitSize() {
        return unitSize;
    }

    /**
     * Gets min dist.
     *
     * @return the min dist
     */
    public float getMinDist() {
        return minDist;
    }

    /**
     * Sets min dist.
     *
     * @param minDist the min dist
     */
    public void setMinDist(float minDist) {
        this.minDist = minDist;
    }

    /**
     * Gets player one right border y.
     *
     * @return the player one right border y
     */
    public float getPlayerOneRightBorderY() {
        return playerOneRightBorderY;
    }

    /**
     * Sets player one right border y.
     *
     * @param playerOneRightBorderY the player one right border y
     */
    public void setPlayerOneRightBorderY(float playerOneRightBorderY) {
        this.playerOneRightBorderY = playerOneRightBorderY;
    }

    /**
     * Gets player one left border y.
     *
     * @return the player one left border y
     */
    public float getPlayerOneLeftBorderY() {
        return playerOneLeftBorderY;
    }

    /**
     * Sets player one left border y.
     *
     * @param playerOneLeftBorderY the player one left border y
     */
    public void setPlayerOneLeftBorderY(float playerOneLeftBorderY) {
        this.playerOneLeftBorderY = playerOneLeftBorderY;
    }

    /**
     * Gets player two right border y.
     *
     * @return the player two right border y
     */
    public float getPlayerTwoRightBorderY() {
        return playerTwoRightBorderY;
    }

    /**
     * Sets player two right border y.
     *
     * @param playerTwoRightBorderY the player two right border y
     */
    public void setPlayerTwoRightBorderY(float playerTwoRightBorderY) {
        this.playerTwoRightBorderY = playerTwoRightBorderY;
    }

    /**
     * Gets player two left border y.
     *
     * @return the player two left border y
     */
    public float getPlayerTwoLeftBorderY() {
        return playerTwoLeftBorderY;
    }

    /**
     * Sets player two left border y.
     *
     * @param playerTwoLeftBorderY the player two left border y
     */
    public void setPlayerTwoLeftBorderY(float playerTwoLeftBorderY) {
        this.playerTwoLeftBorderY = playerTwoLeftBorderY;
    }

    /**
     * Gets slow step size.
     *
     * @return the slow step size
     */
    public float getSlowStepSize() {
        return slowStepSize;
    }

    /**
     * Sets slow step size.
     *
     * @param slowStepSize the slow step size
     */
    public void setSlowStepSize(float slowStepSize) {
        this.slowStepSize = slowStepSize;
    }

    /**
     * Gets medium step size.
     *
     * @return the medium step size
     */
    public float getMediumStepSize() {
        return mediumStepSize;
    }

    /**
     * Sets medium step size.
     *
     * @param mediumStepSize the medium step size
     */
    public void setMediumStepSize(float mediumStepSize) {
        this.mediumStepSize = mediumStepSize;
    }

    /**
     * Gets fast step size.
     *
     * @return the fast step size
     */
    public float getFastStepSize() {
        return fastStepSize;
    }

    /**
     * Sets fast step size.
     *
     * @param fastStepSize the fast step size
     */
    public void setFastStepSize(float fastStepSize) {
        this.fastStepSize = fastStepSize;
    }
}
