package clashroyale.models;

import clashroyale.constants.GameConstants;
import clashroyale.models.cardsmodels.spells.Rage;
import clashroyale.models.cardsmodels.troops.Card;
import clashroyale.models.factory.CardFactory;
import clashroyale.models.game.BattleDeck;
import clashroyale.models.game.GameHistory;
import clashroyale.persistence.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type User model.
 */
public class UserModel {

    private static final Logger LOGGER = Logger.getLogger(UserModel.class.getName());

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
    private int elixirCount;

    public UserModel() {
        cards = new ArrayList<>();
        chosenCardsList = new ArrayList<>();
        elixirCount = GameConstants.ELIXIR_INITIAL;
    }

    private Card findByTitle(String string) {
        if (string == null) return null;
        for (Card card : cards) {
            card.setRelatedUser(username);
            if (card instanceof Rage rage) {
                LOGGER.fine(() -> "Rage at level " + rage.getDuration());
            }
            if (string.equals(card.getTitle())) {
                return card;
            }
        }
        LOGGER.fine(() -> "No card matched title '" + string + "'");
        return null;
    }

    /**
     * Seed this user's collection with one fresh instance of every card,
     * scaled to their current level. Centralised in {@link CardFactory}.
     */
    private void addAllCards() {
        cards.addAll(CardFactory.createAll(getLevel(), username));
    }

    /**
     * Loads the user's chosen 8-card deck from the database.
     *
     * <p>Hardened: parameterised query (no SQL injection), bounded
     * {@code SELECT}, deterministic resource cleanup.</p>
     */
    private void getChosenCardsFromDB() {
        final String sql = "SELECT card_1, card_2, card_3, card_4, card_5, card_6, card_7, card_8 "
                         + "FROM chosencards WHERE user_id = ?";
        try (Connection con = ConnectionFactory.open();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return;
                for (int i = 1; i <= GameConstants.DECK_SIZE; i++) {
                    Card card = findByTitle(rs.getString("card_" + i));
                    if (card != null) chosenCardsList.add(card);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to load chosen cards for user " + id, e);
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

    public int getElixirCount() {
        return elixirCount;
    }

    public void setElixirCount(int elixirCount) {
        this.elixirCount = elixirCount;
    }
}
