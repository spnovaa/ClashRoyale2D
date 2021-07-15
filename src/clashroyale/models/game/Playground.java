package clashroyale.models.game;

import clashroyale.models.UserModel;

/**
 * The type Playground.
 */
public class Playground {
    private UserModel player1;
    private UserModel player2;
    private int rightBorderStatus;
    private int leftBorderStatus;
    private LeftTime leftTime;

    /**
     * Instantiates a new Playground.
     *
     * @param player1 the player 1
     * @param player2 the player 2
     */
    public Playground(UserModel player1, UserModel player2) {
        this.player1 = player1;
        this.player2 = player2;
        rightBorderStatus = 0;
        leftBorderStatus = 0;
        leftTime = new LeftTime();
    }

    /**
     * Gets player 1.
     *
     * @return the player 1
     */
    public UserModel getPlayer1() {
        return player1;
    }

    /**
     * Sets player 1.
     *
     * @param player1 the player 1
     */
    public void setPlayer1(UserModel player1) {
        this.player1 = player1;
    }

    /**
     * Gets player 2.
     *
     * @return the player 2
     */
    public UserModel getPlayer2() {
        return player2;
    }

    /**
     * Sets player 2.
     *
     * @param player2 the player 2
     */
    public void setPlayer2(UserModel player2) {
        this.player2 = player2;
    }

    /**
     * Gets right border status.
     *
     * @return the right border status
     */
    public int getRightBorderStatus() {
        return rightBorderStatus;
    }

    /**
     * Sets right border status.
     *
     * @param rightBorderStatus the right border status
     */
    public void setRightBorderStatus(int rightBorderStatus) {
        this.rightBorderStatus = rightBorderStatus;
    }

    /**
     * Gets left border status.
     *
     * @return the left border status
     */
    public int getLeftBorderStatus() {
        return leftBorderStatus;
    }

    /**
     * Sets left border status.
     *
     * @param leftBorderStatus the left border status
     */
    public void setLeftBorderStatus(int leftBorderStatus) {
        this.leftBorderStatus = leftBorderStatus;
    }

    /**
     * Gets left time.
     *
     * @return the left time
     */
    public LeftTime getLeftTime() {
        return leftTime;
    }

    /**
     * Sets left time.
     *
     * @param leftTime the left time
     */
    public void setLeftTime(LeftTime leftTime) {
        this.leftTime = leftTime;
    }
}
