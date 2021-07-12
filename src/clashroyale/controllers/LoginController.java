package clashroyale.controllers;

import clashroyale.views.AppAlerts;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import clashroyale.models.LoginModel;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Login controller.
 */
public class LoginController extends Application {
    private Scene menu;
    private MenuController menuController;
    private Stage stage;

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    private LoginModel loginModel = new LoginModel();

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
    }

    /**
     * Login process.
     */
    @FXML
    public void loginProcess() {
        //tries to login the given username and password first and shows the appropriate massage.
        int loginResult = loginModel.tryToLogin(username.getText(), password.getText());

        if (loginResult == 0) {
            int regResult = loginModel.tryToRegister(username.getText(), password.getText());

            if (regResult == 1) {
                System.out.println("REGISTERED SUCCESSFULLY");
                new AppAlerts("Welcome!", null, "Registered successfully!")
                        .showInformationAlert();
                loginProcess();

            } else if (regResult == -1) {
                System.out.println("Connection Failed!");
                new AppAlerts("Error!", null, "Connection Failed!").showInformationAlert();
            }

        } else {
            if (loginResult == -1) {
                System.out.println("Connection Failed!");
                new AppAlerts("Error!", null, "Connection Failed!").showInformationAlert();

            } else if (loginResult == 1) {
                System.out.println("LOGGED IN SUCCESSFULLY!");
                new AppAlerts("Welcome!", null, "Logged In Successfully").showInformationAlert();
                goToMenu();
            } else if (loginResult == 2) {
                System.out.println("INCORRECT PASSWORD!");
                new AppAlerts("ERROR!", null, "Wrong Credentials! Try Again!\n")
                        .showInformationAlert();
            }
        }
    }

    private void goToMenu() {
        if (menu == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Menu.fxml"));
                Parent menuRoot = loader.load();
                menuController = loader.getController();
                menuController.start(stage);

                menu = new Scene(menuRoot);

            } catch (Exception exception) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, exception);
                exception.printStackTrace();
            }
        }
        stage.setScene(menu);
    }


}
