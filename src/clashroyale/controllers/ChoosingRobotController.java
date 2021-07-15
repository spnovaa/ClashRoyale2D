package clashroyale.controllers;

import clashroyale.models.game.Robot;
import clashroyale.models.game.SimpleRobot;
import clashroyale.models.game.SmartRobot;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.event.ActionEvent;

/**
 * The type Choosing robot controller.
 */
public class ChoosingRobotController {
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

    private void saveInDataBase() {
    }

}
