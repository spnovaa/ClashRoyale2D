package clashroyale.controllers;

import clashroyale.models.UserModel;
import javafx.application.Application;
import javafx.stage.Stage;

public class HistoryController extends Application {
    private UserModel userModel;

    @Override
    public void start(Stage stage) throws Exception {

    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
        getHistoryList();
    }

    private void getHistoryList() {
    }
}
