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

/**
 * The type Choosing cards controller.
 */
public class ChoosingCardsController {

    @FXML
    private ListView<Card> cardsListView;

    @FXML
    private ListView<Card> chosenCardsListView;

    private final ObservableList<Card> cards =
            FXCollections.observableArrayList();
    private ObservableList<Card> chosenCards;

    /**
     * Get chosen card observable list.
     *
     * @return the observable list
     */
    public ObservableList<Card> getChosenCard (){
        //get from data base
        //if it is empty return null
        return null;
    }


    /**
     * Get level int.
     *
     * @return the int
     */
//return player's level
    public int getLevel(){

        return 0;}

    /**
     * Gets selection card from cards.
     *
     * @param event the event
     */
    @FXML
    void getSelectionCardFromCards(MouseEvent event) {
        Card card =(Card) (chosenCardsListView.getSelectionModel().getSelectedItem());
        if (chosenCards.size() <8){
            chosenCards.add(card);
            chosenCardsListView.setItems(chosenCards);
        }

    }

    /**
     * Gets chosen card selection.
     *
     * @param event the event
     */
    @FXML
    void getChosenCardSelection(MouseEvent event) {
        Card card =(Card) (chosenCardsListView.getSelectionModel().getSelectedItem());
        chosenCards.remove(card);
        chosenCardsListView.setItems(chosenCards);

    }

    /**
     * Initialize.
     */
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
