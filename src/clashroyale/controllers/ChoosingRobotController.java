package clashroyale.controllers;

import clashroyale.models.DbConnect;
import clashroyale.models.UserModel;
import clashroyale.models.game.Robot;
import clashroyale.models.game.SimpleRobot;
import clashroyale.models.game.SmartRobot;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Choosing robot controller.
 */
public class ChoosingRobotController extends Application {

    private Scene menu;
    private MenuController menuController;
    private Stage stage;
    private UserModel userModel;
    /**
     * The Robot.
     */
    Robot robot;

    @FXML
    private RadioButton SimpleRobot;

    @FXML
    private ToggleGroup RobotType;

    @FXML
    private RadioButton SmartRobot;

    /**
     * Initialize.
     */
    public  void initialize(){
        SmartRobot.setUserData(new SmartRobot());
        SimpleRobot.setUserData(new SimpleRobot());
    }

    /**
     * Robot type selected.
     *
     * @param event the event
     */
    @FXML
    void robotTypeSelected(ActionEvent event) {
        robot = (Robot) RobotType.getSelectedToggle().getUserData();
        saveInDataBase();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
    }

    /**
     * Sets user model.
     *
     * @param userModel the user model
     */
    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    /**
     * Save in data base.
     */
    public void saveInDataBase() {
        int type = -1;
        if (robot instanceof SmartRobot) {
            System.out.println("Player Chose SmartRobot");
            type = 0;
        } else if (robot instanceof SimpleRobot) {
            System.out.println("Player Chose SimpleRobot");
            type = 1;
        } else
            System.err.println("ERR: Unknown Action!");
        if (!(type == -1)) {
            saveType(type);
            goToMenu();
        }
    }

    private void saveType(int type) {
        try {
            Connection con = new DbConnect().getConnection();
            if (con == null) throw new SQLException("CONNECTION FAILED");
            String insertion = "UPDATE users set bot_type = ? where id = ?";
            PreparedStatement st = con.prepareStatement(insertion);
            st.setString(1, String.valueOf(type));
            st.setString(2, String.valueOf(userModel.getId()));
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
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
