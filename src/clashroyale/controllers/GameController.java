package clashroyale.controllers;

import clashroyale.models.GameModel;
import clashroyale.models.UserModel;
import clashroyale.models.cardsmodels.buildings.Building;
import clashroyale.models.cardsmodels.buildings.Cannon;
import clashroyale.models.cardsmodels.buildings.InfernoTower;
import clashroyale.models.cardsmodels.spells.Arrows;
import clashroyale.models.cardsmodels.spells.Fireball;
import clashroyale.models.cardsmodels.spells.Rage;
import clashroyale.models.cardsmodels.spells.Spells;
import clashroyale.models.cardsmodels.troops.*;
import clashroyale.models.game.Robot;
import clashroyale.models.game.SimpleRobot;
import clashroyale.models.game.SmartRobot;
import clashroyale.models.towersmodels.Tower;
import clashroyale.views.GameView;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * The type Game controller.
 */
public class GameController extends Application {
    /**
     * The Game view.
     */
    @FXML
    GameView gameView;
    /**
     * The Anchor pane.
     */
    @FXML
    AnchorPane anchorPane;
    /**
     * The Displayed card 1.
     */
    @FXML
    ImageView displayedCard1;
    /**
     * The Displayed card 2.
     */
    @FXML
    ImageView displayedCard2;
    /**
     * The Displayed card 3.
     */
    @FXML
    ImageView displayedCard3;
    /**
     * The Displayed card 4.
     */
    @FXML
    ImageView displayedCard4;
    /**
     * The Next card.
     */
    @FXML
    ImageView nextCard;
    /**
     * The left time.
     */
    @FXML
    Label time;

    @FXML
    ImageView botKing;
    @FXML
    ImageView userKing;
    @FXML
    ImageView botRightQueen;
    @FXML
    ImageView userRightQueen;
    @FXML
    ImageView botLeftQueen;
    @FXML
    ImageView userLeftQueen;
    private final static double FRAMES_PER_SECOND = 15.0;
    @FXML
    Label userElixir;
    @FXML
    private Label botCrown1;

    @FXML
    private Label playerCrown1;

    private Stage stage;
    private UserModel userModel;
    @FXML
    Label botElixir;
    private Scene gameScene;

    private int userMinX;
    private int userMaxX;
    private int userMinY;
    private int userMaxY;
    private GameModel gameModel;
    private Timer timer;
    int botMaxTroops = 80;
    private Card botChosenCard;
    private Point2D botClickCoordinates;

    private ArrayList<Tower> arenaTowers;
    private Robot bot;
    private int botActFlag;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
    }

    /**
     * Instantiates a new Game controller.
     */
    public GameController() {
        userMinX = 25;
        userMaxX = 340;
        userMinY = 255;
        userMaxY = 450;
        arenaTowers = new ArrayList<>();
        botActFlag = 0;
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
     * Sets game scene.
     *
     * @param gameScene the game scene
     */
    public void setGameScene(Scene gameScene) {
        this.gameScene = gameScene;
        addClickedLocationListener();
        gameView = new GameView(userModel);
        gameView.setTextTime(time);
        gameView.setBotCrown(botCrown1);
        gameView.setPlayerCrown(playerCrown1);
        gameView.setUserElixir(userElixir);
        gameView.setBotElixir(botElixir);
        gameView.setAnchorPane(anchorPane);
        gameView.instantiateCardsQueImageViews(displayedCard1, displayedCard2, displayedCard3, displayedCard4, nextCard);
        gameView.instantiateTowers(botKing, botLeftQueen, botRightQueen, userKing, userLeftQueen, userRightQueen);
        arenaTowers = gameModel.getArenaTowers();
        gameView.setArenaTowers(arenaTowers);
        gameView.prepareArena();
        System.out.println(userModel.getLevel());
        this.bot = gameModel.getGameBot();
        startTimer();
    }

    /**
     * Add clicked location listener.
     */
    public void addClickedLocationListener() {
        gameScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                float x = (float) event.getSceneX();
                float y = (float) event.getSceneY();
                boolean isCardDeployed = deployClickedAt(x, y);
            }
        });

    }

    /**
     * deploy intended card to intended position and change the view
     *
     * @param x clicked x
     * @param y clicked y
     */
    private boolean deployClickedAt(float x, float y) {
        Card chosen = userModel.getChosenToDeployCard();
        boolean isInRange = y < userMaxY && y > userMinY && x > userMinX && x < userMaxX;
        boolean hasElixirs = false;
        if (chosen != null)
            hasElixirs = chosen.getCost() <= userModel.getElixirCount();
        if (chosen != null && isInRange && hasElixirs) {
            userModel.setElixirCount(userModel.getElixirCount() - chosen.getCost());
            chosen = generateNewCard(chosen.getTitle(), userModel.getUsername());
            gameView.deployTroops(x, y, chosen);
            chosen.setCenterPositionX(x);
            chosen.setCenterPositionY(y);
            if (chosen instanceof Spells) {
                //add to existing spells
                gameModel.spellAction(chosen);
                gameModel.getArenaExistingSpellCards().add((Spells) chosen);
            } else if (chosen instanceof TroopsCard) {
                //add to existing troops
                gameModel.getArenaExistingTroops().add((TroopsCard) chosen);
                System.out.println("Added : " + chosen.getUuid());
            } else {
                //add to existing buildings
                gameModel.getArenaExistingBuildings().add((Building) chosen);
            }
            return true;
        } else if (!hasElixirs) {
            System.out.println("You Have " + userModel.getElixirCount() + " Elixirs and Card Costs " + chosen.getCost());
            return false;
        } else {
            System.out.println("You Can't Deploy Cards There!");
            return false;
        }
    }

    private void deployBotClickedAt(float x, float y) {
//        System.out.println("Bot Clicked At: "+ x + " , "+ y);
        if (botChosenCard != null && botChosenCard.getCost() <= bot.getElixirCount()) {
            bot.setElixirCount(bot.getElixirCount() - botChosenCard.getCost());
            String bot = userModel.getBotType();
            botChosenCard = generateNewCard(botChosenCard.getTitle(), bot);
//            System.out.println(botChosenCard.getRelatedUser());
            gameView.deployTroops(x, y, botChosenCard);
            botChosenCard.setCenterPositionX(x);
            botChosenCard.setCenterPositionY(y);
            if (botChosenCard instanceof Spells) {
                //add to existing spells
                gameModel.getArenaExistingSpellCards().add((Spells) botChosenCard);
                gameModel.spellAction(botChosenCard);
            } else if (botChosenCard instanceof TroopsCard) {
                //add to existing troops
                gameModel.getArenaExistingTroops().add((TroopsCard) botChosenCard);
            } else {
                //add to existing buildings
                gameModel.getArenaExistingBuildings().add((Building) botChosenCard);
            }
        } else if (botChosenCard != null) System.out.println("Bot Chose" + botChosenCard.getTitle() + "with " +
                botChosenCard.getCost() + " cost. but has " + bot.getElixirCount() + " elixirs.");
        else System.out.println("Bot Card Is Null");
    }

    /**
     * Set chosen card index one.
     */
    public void setChosenCardIndexOne() {
        gameView.setChosenCardIndex(1);
        userModel.setChosenToDeployCard((Card) displayedCard1.getUserData());
    }

    /**
     * Set chosen card index two.
     */
    public void setChosenCardIndexTwo() {
        gameView.setChosenCardIndex(2);
        userModel.setChosenToDeployCard((Card) displayedCard2.getUserData());
    }

    /**
     * Set chosen card index three.
     */
    public void setChosenCardIndexThree() {
        gameView.setChosenCardIndex(3);
        userModel.setChosenToDeployCard((Card) displayedCard3.getUserData());
    }

    /**
     * Set chosen card index four.
     */
    public void setChosenCardIndexFour() {
        gameView.setChosenCardIndex(4);
        userModel.setChosenToDeployCard((Card) displayedCard4.getUserData());
    }


    /**
     * Schedules the model to update based on the timer.
     */
    private void startTimer() {
        this.timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        updateGame();
                    }
                });
            }
        };

        long frameTimeInMilliseconds = (long) (1000.0 / FRAMES_PER_SECOND);
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    private synchronized void updateGame() {
        gameView.updateTimer();
        gameView.updateCrown(gameModel.getRobotCrown()+"",gameModel.getPlayerCrown()+"");
        gameModel.updateElixirs();
        gameView.updateElixirs(userModel.getElixirCount(), bot.getElixirCount());
        bot = gameModel.getGameBot();
        botActFlag++;
        if (botActFlag % 15 == 0) actBot();

        gameModel.updateGameModel();
        ArrayList<TroopsCard> existingTroops = gameModel.getArenaExistingTroops();
        ArrayList<Tower> existingTowers = gameModel.getArenaExistingTowers();
        ArrayList<Spells> existingSpells = gameModel.getArenaExistingSpellCards();
        gameView.updateLivingAssets(existingTroops, existingTowers, existingSpells);
    }

    private void actBot() {
        if (bot instanceof SimpleRobot) {
            botChosenCard = ((SimpleRobot) bot).chooseCardToPlay();
            botClickCoordinates = ((SimpleRobot) bot).chooseCoordinatesToPlay();
        } else
            System.out.println("SmartBot Is Not Implemented Yet");
//        System.out.println("Bot Chose: " + botChosenCard.getTitle() + " at " + botClickCoordinates.getX()+" : "+ botClickCoordinates.getY());
        if (botMaxTroops > 0) {
            deployBotClickedAt((float) botClickCoordinates.getX(), (float) botClickCoordinates.getY());
            botMaxTroops--;
        }
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    private Card generateNewCard(String title, String username) {
        Card card;
        int level = gameModel.getUserLevel();
        switch (title) {
            case "cannon" -> card = new Cannon(level, username);
            case "infernoTower" -> card = new InfernoTower(level, username);
            case "arrows" -> card = new Arrows(level, username);
            case "fireball" -> card = new Fireball(level, username);
            case "rage" -> card = new Rage(level, username);
            case "archer" -> card = new ArchersCard(level, username);
            case "babyDragon" -> card = new BabyDragonCard(level, username);
            case "barbarian" -> card = new BarbariansCard(level, username);
            case "giant" -> card = new GiantCard(level, username);
            case "miniPEKKA" -> card = new MiniPEKKACard(level, username);
            case "valkyrie" -> card = new ValkyrieCard(level, username);
            case "wizard" -> card = new WizardCard(level, username);
            default -> card = null;
        }
        return card;
    }


}
