package clashroyale.models.game;

import clashroyale.models.cardsmodels.Card;

import java.util.ArrayList;

/**
 * The type Profile.
 */
public class Profile {

    private int cupsCount;
    private int level;
    private ArrayList<Card> chosenCardsList;

    /**
     * Instantiates a new Profile.
     *
     * @param cupsCount       the cups count
     * @param level           the level
     * @param chosenCardsList the chosen cards list
     */
    public Profile(int cupsCount, int level, ArrayList<Card> chosenCardsList) {
        this.cupsCount = cupsCount;
        this.level = level;
        this.chosenCardsList = chosenCardsList;
    }

    /**
     * Gets cups count.
     *
     * @return the cups count
     */
    public int getCupsCount() {
        return cupsCount;
    }

    /**
     * Sets cups count.
     *
     * @param cupsCount the cups count
     */
    public void setCupsCount(int cupsCount) {
        this.cupsCount = cupsCount;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets chosen cards list.
     *
     * @return the chosen cards list
     */
    public ArrayList<Card> getChosenCardsList() {
        return chosenCardsList;
    }

    /**
     * Sets chosen cards list.
     *
     * @param chosenCardsList the chosen cards list
     */
    public void setChosenCardsList(ArrayList<Card> chosenCardsList) {
        this.chosenCardsList = chosenCardsList;
    }
}
