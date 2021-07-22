package clashroyale.models.game;

import clashroyale.models.GameModel;
import clashroyale.models.cardsmodels.buildings.Building;
import clashroyale.models.cardsmodels.spells.Spells;
import clashroyale.models.cardsmodels.troops.Card;
import clashroyale.models.cardsmodels.troops.TroopsCard;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The type Smart robot.
 */
public class SmartRobot extends Robot {
    ArrayList<Card> smartBotCards;
    GameModel gameModel;
    public SmartRobot(int level) {
        super("smartBot", level);
        smartBotCards = choosingSmartBotCards();
    }
    public void setLiveData(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public ArrayList<Card> choosingSmartBotCards(){
        ArrayList<Card> smartBotCards1 = new ArrayList();
        int troop=0;
        int spell=0;
        int building=0;
        for (Card card : super.getAllCards()){
            if (card instanceof Building){
                smartBotCards1.add(card);
            }
            break;
        }
        for (Card card : super.getAllCards()){
            if (card instanceof TroopsCard){
                smartBotCards1.add(card);
            }
            troop++;
            if (troop==5){
            break;}
        }
        for (Card card : super.getAllCards()){
            if (card instanceof Spells){
                smartBotCards1.add(card);
            }
            spell++;
            if (spell==2){
                break;}
        }
        Collections.shuffle(smartBotCards1);
        return smartBotCards1;
    }
}
