package clashroyale.controllers;

import java.awt.TextField;

import clashroyale.models.LoginModel;
import javafx.fxml.FXML;
import jdk.internal.net.http.common.Log;

public class LoginController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    private LoginModel loginModel=new LoginModel();

    @FXML
    public void loginProcess(){

    }


}
