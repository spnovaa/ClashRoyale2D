package clashroyale.controllers;

import javafx.scene.control.TextField;

import clashroyale.models.LoginModel;
import javafx.fxml.FXML;

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
