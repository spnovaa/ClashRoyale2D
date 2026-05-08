package clashroyale.controllers;

import clashroyale.models.GameModel;
import clashroyale.models.UserModel;
import clashroyale.models.cardsmodels.buildings.Building;
import clashroyale.models.cardsmodels.spells.Spells;
import clashroyale.models.cardsmodels.troops.Card;
import clashroyale.models.cardsmodels.troops.TroopsCard;
import clashroyale.models.enums.CardType;
import clashroyale.models.factory.CardFactory;
import clashroyale.models.game.Robot;
import clashroyale.models.game.SimpleRobot;
import clashroyale.models.game.SmartRobot;
import clashroyale.models.towersmodels.Tower;
import clashroyale.persistence.GameHistoryRepository;
import clashroyale.views.AppAlerts;
import clashroyale.views.GameView;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.control.Label;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * FXML controller for the game scene.
 *
 * <p>Responsibilities (intentionally limited):
 * <ul>
 *   <li>Wire FXML fields to {@link GameView} on scene setup.</li>
 *   <li>Run the game timer and dispatch each tick to model + view.</li>
 *   <li>Translate mouse clicks into card-deploy calls.</li>
 *   <li>Delegate bot decisions to the bot, history saves to
 *       {@link GameHistoryRepository}.</li>
 * </ul>
 * </p>
 */
public class GameController extends Application {

    private static final Logger LOG = Logger.getLogger(GameController.class.getName());

    // ── FXML bindings ──────────────────────────────────────────────────────────
    @FXML GameView    gameView;
    @FXML AnchorPane  anchorPane;
    @FXML ImageView   displayedCard1;
    @FXML ImageView   displayedCard2;
    @FXML ImageView   displayedCard3;
    @FXML ImageView   displayedCard4;
    @FXML ImageView   nextCard;
    @FXML Label       time;
    @FXML ImageView   botKing;
    @FXML ImageView   userKing;
    @FXML ImageView   botRightQueen;
    @FXML ImageView   userRightQueen;
    @FXML ImageView   botLeftQueen;
    @FXML ImageView   userLeftQueen;
    @FXML Label       userElixir;
    @FXML Label       botElixir;
    @FXML private Label botCrown1;
    @FXML private Label playerCrown1;

    // ── Game state ────────────────────────────────────────────────────────────
    private Stage      stage;
    private UserModel  userModel;
    private GameModel  gameModel;
    private Robot      bot;
    private Timer      timer;
    private boolean    isGameRunning;
    private boolean    isAlertShown;

    // ── Bot deployment state ──────────────────────────────────────────────────
    private Card    botChosenCard;
    private Point2D botClickCoordinates;
    private int     botActFlag;
    private int     botMaxTroops;

    // ── Player deployment bounds ──────────────────────────────────────────────
    private static final int USER_MIN_X = 25;
    private static final int USER_MAX_X = 340;
    private static final int USER_MIN_Y = 255;
    private static final int USER_MAX_Y = 450;

    // ── Scene/menu cache ──────────────────────────────────────────────────────
    private Scene          gameScene;
    private Scene          menu;
    private MenuController menuController;

    // ─────────────────────────────────────────────────────────────────────────

    public GameController() {
        isGameRunning = true;
        isAlertShown  = false;
        botActFlag    = 0;
        botMaxTroops  = 800;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
    }

    // ── Setup ─────────────────────────────────────────────────────────────────

    public void setUserModel(UserModel userModel) { this.userModel = userModel; }
    public void setGameModel(GameModel gameModel) { this.gameModel = gameModel; }

    /**
     * Wires all FXML nodes to the view, initialises towers, and starts the
     * game timer. Must be called after both {@code setUserModel} and
     * {@code setGameModel}.
     */
    public void setGameScene(Scene gameScene) {
        this.gameScene = gameScene;
        addClickListener();

        gameView = new GameView(userModel);
        gameView.setTextTime(time);
        gameView.setBotCrown(botCrown1);
        gameView.setPlayerCrown(playerCrown1);
        gameView.setUserElixir(userElixir);
        gameView.setBotElixir(botElixir);
        gameView.setAnchorPane(anchorPane);
        gameView.instantiateCardsQueImageViews(displayedCard1, displayedCard2, displayedCard3, displayedCard4, nextCard);
        gameView.instantiateTowers(botKing, botLeftQueen, botRightQueen, userKing, userLeftQueen, userRightQueen);
        gameView.setArenaTowers(gameModel.getArenaTowers());
        gameView.prepareArena();

        this.bot = gameModel.getGameBot();
        startTimer();
    }

    // ── Card-selection callbacks (called from FXML) ───────────────────────────

    public void setChosenCardIndexOne()   { selectCard(displayedCard1, 1); }
    public void setChosenCardIndexTwo()   { selectCard(displayedCard2, 2); }
    public void setChosenCardIndexThree() { selectCard(displayedCard3, 3); }
    public void setChosenCardIndexFour()  { selectCard(displayedCard4, 4); }

    private void selectCard(ImageView slot, int index) {
        gameView.setChosenCardIndex(index);
        userModel.setChosenToDeployCard((Card) slot.getUserData());
    }

    // ── Mouse listener ────────────────────────────────────────────────────────

    private void addClickListener() {
        gameScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                deployUserClickedAt((float) event.getSceneX(), (float) event.getSceneY());
            }
        });
    }

    // ── Card deployment ───────────────────────────────────────────────────────

    private void deployUserClickedAt(float x, float y) {
        Card chosen = userModel.getChosenToDeployCard();
        if (chosen == null) return;

        boolean inBounds   = (y < USER_MAX_Y && y > USER_MIN_Y && x > USER_MIN_X && x < USER_MAX_X)
                             || (chosen instanceof Spells && y < USER_MAX_Y);
        boolean hasElixir  = chosen.getCost() <= userModel.getElixirCount();

        if (!inBounds)   { LOG.fine("Deploy rejected: out of bounds"); return; }
        if (!hasElixir)  { LOG.fine(() -> "Deploy rejected: need " + chosen.getCost() + " have " + userModel.getElixirCount()); return; }

        userModel.setElixirCount(userModel.getElixirCount() - chosen.getCost());
        Card deployed = spawnCard(chosen.getTitle(), userModel.getUsername());
        placeCard(x, y, deployed);
    }

    private void deployBotClickedAt(float x, float y) {
        if (botChosenCard == null || botChosenCard.getCost() > bot.getElixirCount()) return;

        bot.setElixirCount(bot.getElixirCount() - botChosenCard.getCost());
        Card deployed = spawnCard(botChosenCard.getTitle(), userModel.getBotType());
        placeCard(x, y, deployed);
    }

    /** Creates an image, adds it to the arena, and registers the card with the model. */
    private void placeCard(float x, float y, Card card) {
        gameView.deployTroops(x, y, card);
        card.setCenterPositionX(x);
        card.setCenterPositionY(y);
        if (card instanceof Spells s) {
            gameModel.spellAction(card);
            gameModel.getArenaExistingSpellCards().add(s);
        } else if (card instanceof TroopsCard tc) {
            gameModel.getArenaExistingTroops().add(tc);
        } else if (card instanceof Building b) {
            gameModel.getArenaExistingBuildings().add(b);
        }
    }

    /**
     * Creates a fresh card instance. Delegates to {@link CardFactory} via the
     * {@link CardType} enum so there is no switch duplication.
     */
    private Card spawnCard(String title, String username) {
        CardType type = CardType.fromLegacyTitle(title);
        if (type == null) {
            LOG.warning(() -> "Unknown card title for spawn: " + title);
            return null;
        }
        return CardFactory.create(type, gameModel.getUserLevel(), username);
    }

    // ── Game timer ────────────────────────────────────────────────────────────

    private void startTimer() {
        this.timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                if (!isGameRunning) {
                    Platform.runLater(() -> saveGameHistory());
                    timer.cancel();
                    timer.purge();
                }
                Platform.runLater(() -> updateGame());
            }
        };
        this.timer.schedule(task, 0L, 1_000L / 15);
    }

    private synchronized void updateGame() {
        boolean timeUp = gameView.getLeftTime().getMinutes() == 0
                      && gameView.getLeftTime().getSeconds() == 0;
        isGameRunning  = !(gameModel.getRobotCrown() == 3
                        || gameModel.getPlayerCrown() == 3
                        || timeUp);

        if (isGameRunning) {
            tickModel();
            tickView();
        } else if (!isAlertShown) {
            showEndAlert();
            isAlertShown = true;
        }
    }

    private void tickModel() {
        gameModel.updateElixirs();
        bot = gameModel.getGameBot();
        botActFlag++;
        if (botActFlag % 15 == 0) actBot();
        gameModel.updateGameModel();
    }

    private void tickView() {
        gameView.updateTimer();
        gameView.updateCrown(gameModel.getRobotCrown() + "", gameModel.getPlayerCrown() + "");
        gameView.updateElixirs(userModel.getElixirCount(), bot.getElixirCount());

        ArrayList<TroopsCard> troops    = gameModel.getArenaExistingTroops();
        ArrayList<Tower>      towers    = gameModel.getArenaExistingTowers();
        ArrayList<Spells>     spells    = gameModel.getArenaExistingSpellCards();
        ArrayList<Building>   buildings = gameModel.getArenaExistingBuildings();
        gameView.updateLivingAssets(troops, towers, spells, buildings);
    }

    // ── Bot AI ────────────────────────────────────────────────────────────────

    /**
     * Asks the active bot to choose a card and deployment location, then
     * deploys it.  The {@code if/else} is now properly exclusive so a
     * {@code SimpleRobot} can never trigger the {@code SmartRobot} branch.
     */
    private void actBot() {
        if (bot instanceof SmartRobot smart) {
            smart.setLiveData(gameModel);
            botChosenCard = smart.chooseCardToPlay();
            if (botChosenCard != null) {
                botClickCoordinates = smart.chooseCoordinatesToPlay(botChosenCard);
            }
        } else if (bot instanceof SimpleRobot simple) {
            botChosenCard       = simple.chooseCardToPlay();
            botClickCoordinates = simple.chooseCoordinatesToPlay();
        }

        if (botMaxTroops > 0 && botClickCoordinates != null) {
            deployBotClickedAt((float) botClickCoordinates.getX(), (float) botClickCoordinates.getY());
            botMaxTroops--;
        }
    }

    // ── End-of-game ───────────────────────────────────────────────────────────

    private void showEndAlert() {
        boolean won = gameModel.getPlayerCrown() > gameModel.getRobotCrown();
        String  msg = won
                ? " You Won And Received 3 Crowns!"
                : "Bot Won And You Received " + gameModel.getPlayerCrown() + " Crowns!";
        new AppAlerts("Game Finished!", " GGWP!", msg + "\nPress OK To Redirect Menu!")
                .showInformationAlert();
        isGameRunning = false;
    }

    private void saveGameHistory() {
        try {
            new GameHistoryRepository().save(userModel, bot, gameModel, gameView.getLeftTime());
            goToMenu();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Failed to save game history", e);
            new AppAlerts("ERROR!", null, "An Error Occurred While saving To Database!")
                    .showInformationAlert();
        }
    }

    private void goToMenu() {
        if (menu == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Menu.fxml"));
                Parent root = loader.load();
                menuController = loader.getController();
                menuController.setUserModel(userModel);
                menuController.start(stage);
                menu = new Scene(root);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "Failed to load menu scene", e);
            }
        }
        stage.setHeight(538);
        stage.setWidth(320);
        stage.setScene(menu);
    }
}
