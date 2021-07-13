package clashroyale.controllers;

import clashroyale.models.ImageTextCell;
import clashroyale.models.cardsmodels.buildings.Cannon;
import clashroyale.models.cardsmodels.buildings.Fireball;
import clashroyale.models.cardsmodels.buildings.InfernoTower;
import clashroyale.models.cardsmodels.spells.Arrows;
import clashroyale.models.cardsmodels.spells.Rage;
import clashroyale.models.cardsmodels.troops.*;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ListCell;
import javafx.util.Callback;

public class ChoosingCardsController {

    @FXML
    private ListView<Card> cardsListView;

    @FXML
    private ListView<Card> chosenCardsListView;

    private final ObservableList<Card> cards =
            FXCollections.observableArrayList();
    private ObservableList<Card> chosenCards;

    public ObservableList<Card> getChosenCard (){
        //get from data base
        //if it is empty return null
        return null;
    }




    //return player's level
    public int getLevel(){

        return 0;}

    @FXML
    void getSelectionCardFromCards(MouseEvent event) {

    }

    public void initialize(){
        chosenCards=getChosenCard();
        cards.add(new ArchersCard(getLevel()));
        cards.add(new BarbariansCard(getLevel()));
        cards.add(new BabyDragonCard(getLevel()));
        cards.add(new GiantCard(getLevel()));
        cards.add(new MiniPEKKACard(getLevel()));
        cards.add(new ValkyrieCard(getLevel()));
        cards.add(new WizardCard(getLevel()));
        cards.add(new Cannon(getLevel()));
        cards.add(new InfernoTower(getLevel()));
        cards.add(new Fireball(getLevel()));
        cards.add(new Rage(getLevel()));
        cards.add(new Arrows(getLevel()));

        cardsListView.setItems(cards);
        chosenCardsListView.setItems(chosenCards);

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




    }

}
