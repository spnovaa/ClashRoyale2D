package clashroyale.models;

import clashroyale.models.cardsmodels.buildings.Cannon;
import clashroyale.models.cardsmodels.spells.Fireball;
import clashroyale.models.cardsmodels.buildings.InfernoTower;
import clashroyale.models.cardsmodels.spells.Arrows;
import clashroyale.models.cardsmodels.spells.Rage;
import clashroyale.models.cardsmodels.troops.*;
import clashroyale.models.game.BattleDeck;
import clashroyale.models.game.GameHistory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * The type User model.
 */
public class UserModel {
    private String id;
    private String username;
    private ArrayList<Card> cards;
    private int cupsCount;
    private int level;
    private ArrayList<Card> chosenCardsList;
    private GameHistory gameHistory;
    private BattleDeck battleDeck;
    private Card chosenToDeployCard;
    private String botType;

    /**
     * Instantiates a new User model.
     */
    public UserModel() {
        cards = new ArrayList<>();
        chosenCardsList = new ArrayList<>();
    }

    private Card findByTitle(String string) {
        for (Card card : cards) {
            card.setRelatedUser(username);
            if (card.getTitle().equals(string))
                return card;
        }
        return null;
    }

    private void addAllCards() {
        cards.add(new ArchersCard(getLevel(), username));
        cards.add(new BarbariansCard(getLevel(), username));
        cards.add(new BabyDragonCard(getLevel(), username));
        cards.add(new GiantCard(getLevel(), username));
        cards.add(new MiniPEKKACard(getLevel(), username));
        cards.add(new ValkyrieCard(getLevel(), username));
        cards.add(new WizardCard(getLevel(), username));
        cards.add(new Cannon(getLevel(), username));
        cards.add(new InfernoTower(getLevel(), username));
        cards.add(new Fireball(getLevel(), username));
        cards.add(new Rage(getLevel(), username));
        cards.add(new Arrows(getLevel(), username));
    }

    private void getChosenCardsFromDB() {
        try {
            Connection con = new DbConnect().getConnection();
            if (con == null) throw new SQLException("CONNECTION FAILED!");
            Statement statement = con.createStatement();
            String query = "SELECT * FROM chosencards WHERE user_id ='" + id + "'";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                for (int i = 1; i < 9; i++) {
                    Card card = findByTitle(resultSet.getString("card_" + i));
                    if (card != null) {
                        chosenCardsList.add(card);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets game history.
     *
     * @return the game history
     */
    public GameHistory getGameHistory() {
        return gameHistory;
    }

    /**
     * Sets game history.
     *
     * @param gameHistory the game history
     */
    public void setGameHistory(GameHistory gameHistory) {
        this.gameHistory = gameHistory;
    }

    /**
     * Gets battle deck.
     *
     * @return the battle deck
     */
    public BattleDeck getBattleDeck() {
        return battleDeck;
    }

    /**
     * Sets battle deck.
     *
     * @param battleDeck the battle deck
     */
    public void setBattleDeck(BattleDeck battleDeck) {
        this.battleDeck = battleDeck;
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

    /**
     * Get level int.
     *
     * @return the int
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
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
        addAllCards();
        getChosenCardsFromDB();
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets cards.
     *
     * @return the cards
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Sets cards.
     *
     * @param cards the cards
     */
    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public Card getChosenToDeployCard() {
        return chosenToDeployCard;
    }

    public void setChosenToDeployCard(Card chosenToDeployCard) {
        this.chosenToDeployCard = chosenToDeployCard;
    }

    public String getBotType() {
        return botType;
    }

    public void setBotType(String botType) {
        this.botType = botType;
    }
}
