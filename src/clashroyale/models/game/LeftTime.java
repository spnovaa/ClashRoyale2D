package clashroyale.models.game;

/**
 * The type Left time.
 */
public class LeftTime {
    private int minutes;
    private int seconds;

    /**
     * Instantiates a new Left time.
     */
    public LeftTime() {
        minutes = 3;
        seconds = 0;
    }

    /**
     * Gets minutes.
     *
     * @return the minutes
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Sets minutes.
     *
     * @param minutes the minutes
     */
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    /**
     * Gets seconds.
     *
     * @return the seconds
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * Sets seconds.
     *
     * @param seconds the seconds
     */
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    /**
     * Decrease boolean.
     *
     * @return the boolean
     */
    public boolean decrease() {
        if (seconds == 0 && minutes == 0) {
            return false;
        } else {
            if (seconds == 0) {
                seconds = 59;
                minutes--;
            } else
                seconds--;
            return true;
        }
    }
}
