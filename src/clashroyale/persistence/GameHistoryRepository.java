package clashroyale.persistence;

import clashroyale.models.GameModel;
import clashroyale.models.UserModel;
import clashroyale.models.game.LeftTime;
import clashroyale.models.game.Robot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Persists a completed game's summary to the {@code history} table.
 *
 * <p>Extracted from {@code GameController} to separate persistence concerns
 * from UI orchestration. Uses try-with-resources so the connection is always
 * closed, even when an exception is thrown.</p>
 */
public class GameHistoryRepository {

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    private static final String INSERT_SQL =
            "INSERT INTO history "
            + "  (user_id, opponent, user_crowns, opponent_crowns, "
            + "   user_damage, opponent_damage, duration, game_time) "
            + "VALUES (?,?,?,?,?,?,?,?)";

    /** Match duration is calculated from {@code 180 s − leftTime}. */
    private static final int MATCH_TOTAL_SECONDS = 180;

    /**
     * Saves the outcome of a finished match.
     *
     * @param userModel the human player
     * @param bot       the AI opponent
     * @param gameModel final game state (crowns, HP lost)
     * @param leftTime  time remaining on the clock when the match ended
     * @throws SQLException if the INSERT fails or the connection cannot be opened
     */
    public void save(UserModel userModel,
                     Robot     bot,
                     GameModel gameModel,
                     LeftTime  leftTime) throws SQLException {

        String duration = formatDuration(leftTime);
        String date     = DTF.format(LocalDateTime.now());

        try (Connection        con = ConnectionFactory.open();
             PreparedStatement st  = con.prepareStatement(INSERT_SQL)) {

            st.setInt   (1, Integer.parseInt(userModel.getId()));
            st.setString(2, bot.getUsername());
            st.setInt   (3, gameModel.getPlayerCrown());
            st.setInt   (4, gameModel.getRobotCrown());
            st.setInt   (5, gameModel.getPlayerLostHP());
            st.setInt   (6, gameModel.getBotLostHP());
            st.setString(7, duration);
            st.setString(8, date);
            st.executeUpdate();
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private String formatDuration(LeftTime leftTime) {
        int leftSeconds     = leftTime.getMinutes() * 60 + leftTime.getSeconds();
        int durationSeconds = MATCH_TOTAL_SECONDS - leftSeconds;
        return (durationSeconds / 60) + " : " + (durationSeconds % 60);
    }
}
