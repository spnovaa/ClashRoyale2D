package clashroyale.controllers;

import clashroyale.models.UserModel;
import clashroyale.views.GameView;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GameController extends Application {
    @FXML
    GameView gameView;
    @FXML
    AnchorPane anchorPane;
    @FXML
    ImageView displayedCard1;
    @FXML
    ImageView displayedCard2;
    @FXML
    ImageView displayedCard3;
    @FXML
    ImageView displayedCard4;
    @FXML
    ImageView nextCard;
    private Stage stage;
    private UserModel userModel;
    private Scene gameScene;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public void setGameScene(Scene gameScene) {
        this.gameScene = gameScene;
        addClickedLocationListener();
        gameView = new GameView(userModel);
        gameView.setAnchorPane(anchorPane);
        gameView.instantiateCardsQueImageViews(displayedCard1, displayedCard2, displayedCard3, displayedCard4, nextCard);
        gameView.prepareArena();
    }

    public void addClickedLocationListener() {
        gameScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                deployClickedAt((float) event.getSceneX(), (float) event.getSceneY());
            }
        });

    }

    private void deployClickedAt(float x, float y) {
        if (userModel.getChosenToDeployCard() != null)
            gameView.deployTroops(x, y, userModel.getChosenToDeployCard());
    }
}
