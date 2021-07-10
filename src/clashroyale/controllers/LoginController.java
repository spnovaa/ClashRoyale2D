package clashroyale.controllers;

import clashroyale.views.AppAlerts;
import javafx.scene.control.TextField;

import clashroyale.models.LoginModel;
import javafx.fxml.FXML;

/**
 * The type Login controller.
 */
public class LoginController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    private LoginModel loginModel = new LoginModel();

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
                new AppAlerts("Welcome!", null, "Registered and logged in successfully!")
                        .showInformationAlert();

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
            } else if (loginResult == 2) {
                System.out.println("INCORRECT PASSWORD!");
                new AppAlerts("ERROR!", null, "Wrong Credentials! Try Again!\n")
                        .showInformationAlert();
            }
        }
    }


}
