package clashroyale.models.game;

/**
 * The type Player.
 */
public class Player {
    private String username;
    private Profile userProfile;
    private GameHistory gameHistory;
    private BattleDeck battleDeck;

    /**
     * Instantiates a new Player.
     *
     * @param username the username
     */
    public Player(String username) {
        this.username = username;
        this.userProfile = new Profile(0, 0, null);
        this.gameHistory = null;
        this.battleDeck = new BattleDeck(0);
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
     * Gets user profile.
     *
     * @return the user profile
     */
    public Profile getUserProfile() {
        return userProfile;
    }

    /**
     * Sets user profile.
     *
     * @param userProfile the user profile
     */
    public void setUserProfile(Profile userProfile) {
        this.userProfile = userProfile;
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
}
