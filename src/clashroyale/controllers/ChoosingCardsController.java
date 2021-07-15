package clashroyale.controllers;

import clashroyale.models.DbConnect;
import clashroyale.models.ImageTextCell;
import clashroyale.models.UserModel;
import clashroyale.models.cardsmodels.buildings.Cannon;
import clashroyale.models.cardsmodels.buildings.Fireball;
import clashroyale.models.cardsmodels.buildings.InfernoTower;
import clashroyale.models.cardsmodels.spells.Arrows;
import clashroyale.models.cardsmodels.spells.Rage;
import clashroyale.models.cardsmodels.troops.*;
import clashroyale.views.AppAlerts;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Choosing cards controller.
 */
public class ChoosingCardsController extends Application {
    private UserModel userModel;
    private Scene menu;
    private MenuController menuController;
    private Stage stage;

    @FXML
    private ListView<Card> cardsListView;

    @FXML
    private ListView<Card> chosenCardsListView;

    private final ObservableList<Card> cards =
            FXCollections.observableArrayList();
    private ObservableList<Card> chosenCards = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
    }

    /**
     * Get chosen card observable list.
     *
     * @return the observable list
     */
    public void getChosenCardsList() {
        chosenCards.addAll(userModel.getChosenCardsList());
    }


    /**
     * Gets user model.
     *
     * @return the user model
     */
    public UserModel getUserModel() {
        return userModel;
    }

    /**
     * Sets user model.
     *
     * @param userModel the user model
     */
    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
        getChosenCardsList();
        cards.addAll(userModel.getCards());
        cardsListView.setItems(cards);
        chosenCardsListView.setItems(chosenCards);
    }

    /**
     * Initialize.
     */
    public void initialize() {
        cardsListView.setCellFactory(
                new Callback<ListView<Card>, ListCell<Card>>() {
                    @Override
                    public ListCell<Card> call(ListView<Card> listView) {
                        return new ImageTextCell();
                    }
                }
        );

        chosenCardsListView.setCellFactory(
                new Callback<ListView<Card>, ListCell<Card>>() {
                    @Override
                    public ListCell<Card> call(ListView<Card> listView) {
                        return new ImageTextCell();
                    }
                }
        );

        cardsListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Card>() {
                    @Override
                    public void changed(ObservableValue<? extends Card> observableValue, Card card, Card t1) {
                        if (chosenCards.size() < 8) {
                            boolean isChosen = false;
                            for (Card chosenBefore : chosenCards) {
                                if (chosenBefore.getTitle().equals(t1.getTitle())) {
                                    isChosen = true;
                                    break;
                                }
                            }
                            if (!isChosen) {
                                chosenCards.add(t1);
                                chosenCardsListView.setItems(chosenCards);
                            }

                        }
                    }
                }
        );
    }

    /**
     * Save and return.
     */
    public void saveAndReturn() {
        try {
            Connection con = new DbConnect().getConnection();
            if (con == null) throw new SQLException("CONNECTION FAILED");

            String insertion =
                    "REPLACE INTO chosencards(user_id,card_1,card_2,card_3,card_4,card_5,card_6,card_7,card_8)" +
                            " VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(insertion);
            st.setInt(1, Integer.parseInt(getUserModel().getId()));

            int index = 2;
            for (Card chosen : chosenCards) {
                st.setString(index, chosen.getTitle());
                index++;
            }
            if (index < 9) {
                for (int i = index; i < 10; i++) {
                    st.setString(i, " ");
                }
            }
            st.executeUpdate();
            ArrayList<Card> list = new ArrayList<>(chosenCards);
            userModel.setChosenCardsList(list);
            new AppAlerts("Success!", null, "BattleDeck Updated Successfully!")
                    .showInformationAlert();
            goToMenu();
        } catch (SQLException e) {
            e.printStackTrace();
            new AppAlerts("ERROR!", null, "An Error Occurred While saving To Database!")
                    .showInformationAlert();
        }


    }

    /**
     * Clear chosen cards list.
     */
    public void clearChosenCardsList() {
        chosenCards = FXCollections.observableArrayList();
        chosenCardsListView.setItems(chosenCards);
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
