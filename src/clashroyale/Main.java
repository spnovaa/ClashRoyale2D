package clashroyale;

import clashroyale.controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "views/login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 310, 500);
        primaryStage.setResizable(false);
        primaryStage.setTitle("CLash Royale 2D");
        scene.getStylesheets().add("style.css");
        LoginController loginController = loader.getController();
        loginController.start(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
