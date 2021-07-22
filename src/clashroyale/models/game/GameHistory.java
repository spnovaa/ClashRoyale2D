package clashroyale.models.game;

import clashroyale.models.cardsmodels.troops.Card;

import java.util.ArrayList;


/**
 * The type Game.
 */
public class GameHistory {
    private int duration;
    private String username1;
    private String username2;
    private int user1CupsCount;
    private int user2CupsCount;
    private int user1DamageToUser2;
    private int user2DamageToUser1;
    private ArrayList<Card> user1Cards;
    private ArrayList<Card> user2Cards;
    private String winner;

    /**
     * Instantiates a new Game History.
     *
     * @param duration           the duration
     * @param username1          the username of player1
     * @param username2          the username of player2
     * @param user1CupsCount     the user 1 cups count
     * @param user2CupsCount     the user 2 cups count
     * @param user1DamageToUser2 the user 1 damage to user 2
     * @param user2DamageToUser1 the user 2 damage to user 1
     * @param user1Cards         the user 1 cards list
     * @param user2Cards         the user 2 cards list
     */
    public GameHistory(int duration, String username1, String username2, int user1CupsCount, int user2CupsCount
            , int user1DamageToUser2, int user2DamageToUser1, ArrayList<Card> user1Cards, ArrayList<Card> user2Cards) {

        this.duration = duration;
        this.username1 = username1;
        this.username2 = username2;
        this.user1CupsCount = user1CupsCount;
        this.user2CupsCount = user2CupsCount;
        this.user1DamageToUser2 = user1DamageToUser2;
        this.user2DamageToUser1 = user2DamageToUser1;
        this.user1Cards = user1Cards;
        this.user2Cards = user2Cards;

        this.winner = findWinner();
    }

    /**
     * Gets duration.
     *
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets duration.
     *
     * @param duration the duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Gets username 1.
     *
     * @return the username 1
     */
    public String getUsername1() {
        return username1;
    }

    /**
     * Sets username 1.
     *
     * @param username1 the username 1
     */
    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    /**
     * Gets username 2.
     *
     * @return the username 2
     */
    public String getUsername2() {
        return username2;
    }

    /**
     * Sets username 2.
     *
     * @param username2 the username 2
     */
    public void setUsername2(String username2) {
        this.username2 = username2;
    }

    /**
     * Gets user 1 cups count.
     *
     * @return the user 1 cups count
     */
    public int getUser1CupsCount() {
        return user1CupsCount;
    }

    /**
     * Sets user 1 cups count.
     *
     * @param user1CupsCount the user 1 cups count
     */
    public void setUser1CupsCount(int user1CupsCount) {
        this.user1CupsCount = user1CupsCount;
    }

    /**
     * Gets user 2 cups count.
     *
     * @return the user 2 cups count
     */
    public int getUser2CupsCount() {
        return user2CupsCount;
    }

    /**
     * Sets user 2 cups count.
     *
     * @param user2CupsCount the user 2 cups count
     */
    public void setUser2CupsCount(int user2CupsCount) {
        this.user2CupsCount = user2CupsCount;
    }

    /**
     * Gets user 1 damage to user 2.
     *
     * @return the user 1 damage to user 2
     */
    public int getUser1DamageToUser2() {
        return user1DamageToUser2;
    }

    /**
     * Sets user 1 damage to user 2.
     *
     * @param user1DamageToUser2 the user 1 damage to user 2
     */
    public void setUser1DamageToUser2(int user1DamageToUser2) {
        this.user1DamageToUser2 = user1DamageToUser2;
    }

    /**
     * Gets user 2 damage to user 1.
     *
     * @return the user 2 damage to user 1
     */
    public int getUser2DamageToUser1() {
        return user2DamageToUser1;
    }

    /**
     * Sets user 2 damage to user 1.
     *
     * @param user2DamageToUser1 the user 2 damage to user 1
     */
    public void setUser2DamageToUser1(int user2DamageToUser1) {
        this.user2DamageToUser1 = user2DamageToUser1;
    }

    /**
     * Gets user 1 cards.
     *
     * @return the user 1 cards
     */
    public ArrayList<Card> getUser1Cards() {
        return user1Cards;
    }

    /**
     * Sets user 1 cards.
     *
     * @param user1Cards the user 1 cards
     */
    public void setUser1Cards(ArrayList<Card> user1Cards) {
        this.user1Cards = user1Cards;
    }

    /**
     * Gets user 2 cards.
     *
     * @return the user 2 cards
     */
    public ArrayList<Card> getUser2Cards() {
        return user2Cards;
    }

    /**
     * Sets user 2 cards.
     *
     * @param user2Cards the user 2 cards
     */
    public void setUser2Cards(ArrayList<Card> user2Cards) {
        this.user2Cards = user2Cards;
    }

    /**
     * Gets winner.
     *
     * @return the winner
     */
    public String getWinner() {
        return winner;
    }

    /**
     * Sets winner.
     *
     * @param winner the winner
     */
    public void setWinner(String winner) {
        this.winner = winner;
    }

    /**
     * find out who won the game.
     *
     * @return winner username.
     */
    private String findWinner() {
        if (user1CupsCount != user2CupsCount) {

            //return username of the player who achieved more cups.
            return user1CupsCount > user2CupsCount ? username1 : username2;
        } else {

            //return username of the player who damaged more to the other player.
            return user1DamageToUser2 > user2DamageToUser1 ? username1 : username2;
        }
    }
}
