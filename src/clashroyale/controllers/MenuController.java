package clashroyale.controllers;

import clashroyale.models.GameModel;
import clashroyale.models.UserModel;
import clashroyale.models.game.Robot;
import clashroyale.models.game.SimpleRobot;
import clashroyale.models.game.SmartRobot;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Menu controller.
 */
public class MenuController extends Application {
    private Scene botSelectionScene;
    private Scene battleDeckScene;
    private Scene arenaScene;
    private Scene historyScene;
    private ChoosingRobotController choosingRobotController;
    private ChoosingCardsController choosingCardsController;
    private GameController gameController;
    private HistoryController historyController;
    private Stage stage;
    private UserModel userModel;
    private Robot bot;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
    }

    /**
     * Go to bot selection.
     */
    public void goToBotSelection() {
        if (botSelectionScene == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/ChoosingRobot.fxml"));
                Parent choosingBotRoot = loader.load();
                choosingRobotController = loader.getController();
                choosingRobotController.setUserModel(userModel);
                choosingRobotController.start(stage);

                botSelectionScene = new Scene(choosingBotRoot);

            } catch (Exception exception) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, exception);
                exception.printStackTrace();
            }
        }
        stage.setScene(botSelectionScene);
    }

    /**
     * Go to battle deck.
     */
    public void goToBattleDeck() {
        if (battleDeckScene == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/ChoosingCards.fxml"));
                Parent choosingBotRoot = loader.load();
                choosingCardsController = loader.getController();
                choosingCardsController.setUserModel(userModel);
                choosingCardsController.start(stage);

                battleDeckScene = new Scene(choosingBotRoot);

            } catch (Exception exception) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, exception);
                exception.printStackTrace();
            }
        }
        stage.setScene(battleDeckScene);
    }

    public void goToArena() {
        if (arenaScene == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Arena.fxml"));
                Parent goToArenaRoot = loader.load();
                gameController = loader.getController();
                gameController.setUserModel(userModel);
                if (userModel.getBotType().equals("simpleBot")) bot = new SimpleRobot(userModel.getLevel());
                else bot = new SmartRobot(userModel.getLevel());
                GameModel gameModel = new GameModel(userModel, bot);
                gameController.setGameModel(gameModel);
                stage.setHeight(630);
                stage.setWidth(375);
                gameController.start(stage);
                arenaScene = new Scene(goToArenaRoot);
                gameController.setGameScene(arenaScene);
            } catch (Exception exception) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, exception);
                exception.printStackTrace();
            }
        }
        stage.setScene(arenaScene);
    }

    public void goToHistory() {
        if (historyScene == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/history.fxml"));
                Parent goToHistoryRoot = loader.load();
                historyController = loader.getController();
                historyController.setStage(stage);
                historyController.setUserModel(userModel);
//                historyController.start(stage);
                historyScene = new Scene(goToHistoryRoot);
                historyScene.getStylesheets().add("style.css");
            } catch (Exception exception) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, exception);
                exception.printStackTrace();
            }
        }
        stage.setScene(historyScene);
    }

    /**
     * Sets user model.
     *
     * @param userModel the user model
     */
    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
