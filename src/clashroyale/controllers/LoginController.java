package clashroyale.controllers;

import clashroyale.models.UserModel;
import clashroyale.views.AppAlerts;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import clashroyale.models.LoginModel;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;
// LoginModel constants used: RESULT_LOGGED_IN, RESULT_WRONG_PASSWORD, etc.

/**
 * The type Login controller.
 */
public class LoginController extends Application {
    private Scene menu;
    private MenuController menuController;
    private Stage stage;
    public UserModel userModel;
    private LoginModel loginModel;

    public LoginController() {
        loginModel = new LoginModel();
        userModel = new UserModel();
    }

    @FXML
    private TextField username;
    @FXML
    private TextField password;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
    }

    private static final Logger LOG = Logger.getLogger(LoginController.class.getName());

    /**
     * Attempt to log in. If the username does not exist, register it
     * silently and try again — preserving the legacy UX.
     */
    @FXML
    public void loginProcess() {
        int result = loginModel.tryToLogin(username.getText(), password.getText(), userModel);
        switch (result) {
            case LoginModel.RESULT_LOGGED_IN          -> handleLoggedIn();
            case LoginModel.RESULT_WRONG_PASSWORD     -> alertError("Wrong credentials. Try again.");
            case LoginModel.RESULT_CONNECTION_FAILED  -> alertError("Connection failed.");
            case LoginModel.RESULT_USERNAME_NOT_FOUND -> handleAutoRegister();
            default -> alertError("Unexpected login result: " + result);
        }
    }

    private void handleLoggedIn() {
        LOG.info("User logged in successfully.");
        new AppAlerts("Welcome!", null, "Logged in successfully").showInformationAlert();
        goToMenu();
    }

    private void handleAutoRegister() {
        int regResult = loginModel.tryToRegister(username.getText(), password.getText());
        switch (regResult) {
            case LoginModel.RESULT_REGISTERED -> {
                LOG.info("New user registered.");
                new AppAlerts("Welcome!", null, "Registered successfully").showInformationAlert();
                loginProcess();
            }
            case LoginModel.RESULT_CONNECTION_FAILED -> alertError("Connection failed.");
            default -> alertError("Registration failed.");
        }
    }

    private void alertError(String message) {
        LOG.warning(message);
        new AppAlerts("Error", null, message).showInformationAlert();
    }

    private void goToMenu() {
        if (menu == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Menu.fxml"));
                Parent menuRoot = loader.load();
                menuController = loader.getController();
                menuController.setUserModel(userModel);
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
