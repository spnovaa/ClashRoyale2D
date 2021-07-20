package clashroyale.controllers;

import clashroyale.models.GameModel;
import clashroyale.models.UserModel;
import clashroyale.models.cardsmodels.buildings.Building;
import clashroyale.models.cardsmodels.spells.Arrows;
import clashroyale.models.cardsmodels.spells.Fireball;
import clashroyale.models.cardsmodels.spells.Rage;
import clashroyale.models.cardsmodels.spells.Spells;
import clashroyale.models.cardsmodels.troops.Card;
import clashroyale.models.cardsmodels.troops.TroopsCard;
import clashroyale.models.towersmodels.Tower;
import clashroyale.views.GameView;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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

    private Stage stage;
    private UserModel userModel;
    private final static double FRAMES_PER_SECOND = 15.0;
    private Scene gameScene;

    private int userMinX;
    private int userMaxX;
    private int userMinY;
    private int userMaxY;
    private GameModel gameModel;
    private Timer timer;

    private ArrayList<Tower> arenaTowers;

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
        gameView.setAnchorPane(anchorPane);
        gameView.instantiateCardsQueImageViews(displayedCard1, displayedCard2, displayedCard3, displayedCard4, nextCard);
        gameView.instantiateTowers(botKing, botLeftQueen, botRightQueen, userKing, userLeftQueen, userRightQueen);
        arenaTowers = gameModel.getArenaTowers();
        gameView.setArenaTowers(arenaTowers);
        gameView.prepareArena();
        System.out.println(userModel.getLevel());
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
        if (chosen != null && y < userMaxY && y > userMinY && x > userMinX && x < userMaxX) {

            gameView.deployTroops(x, y, chosen);
            chosen.setCenterPositionX(x);
            chosen.setCenterPositionY(y);
            if (chosen instanceof Spells) {
                //add to existing spells
                gameModel.spellAction(chosen);
            } else if (chosen instanceof TroopsCard) {
                //add to existing troops
                gameModel.getArenaExistingTroops().add((TroopsCard) chosen);
            } else {
                //add to existing buildings
                gameModel.getArenaExistingBuildings().add((Building) chosen);
            }
            return true;
        } else {
            return false;
        }
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

    private void updateGame() {
        gameView.updateTimer();
        gameModel.updateGameModel();
        ArrayList<TroopsCard> existingTroops = gameModel.getArenaExistingTroops();
        ArrayList<Tower> existingTowers = gameModel.getArenaExistingTowers();
        ArrayList<Spells> existingSpells = gameModel.getArenaExistingSpellCards();
        gameView.updateLivingAssets(existingTroops, existingTowers,existingSpells);
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }
}
