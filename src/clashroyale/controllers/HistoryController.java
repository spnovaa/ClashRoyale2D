package clashroyale.controllers;

import clashroyale.models.DbConnect;
import clashroyale.models.UserModel;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HistoryController extends Group {
    private UserModel userModel;
    private Scene menu;
    private MenuController menuController;

    TableView tableview;
    @FXML
    AnchorPane anchorpane;
    private Stage stage;
    private ObservableList<ObservableList> data;

    public void buildData() {
        Connection c;
        data = FXCollections.observableArrayList();
        try {
            c = new DbConnect().getConnection();
            String SQL = "SELECT * FROM history WHERE user_id ='" + userModel.getId() + "'";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));
                tableview.getColumns().addAll(col);
            }
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
            tableview.setItems(data);
            anchorpane.getChildren().add(tableview);
//            anchorpane.setMaxWidth(320);
//            stage.setHeight(540);
//            stage.setWidth(320);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }


    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
        getHistoryList();
        tableview = new TableView();
        buildData();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void goToMenu() {
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

    private void getHistoryList() {
    }
}
