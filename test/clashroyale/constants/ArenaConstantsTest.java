package clashroyale.constants;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArenaConstantsTest {

    @Test
    void arena_dimensions_are_positive() {
        assertThat(ArenaConstants.ARENA_WIDTH).isPositive();
        assertThat(ArenaConstants.ARENA_HEIGHT).isPositive();
    }

    @Test
    void bridge_geometry_is_internally_consistent() {
        assertThat(ArenaConstants.BRIDGE_LEFT_X).isLessThan(ArenaConstants.BRIDGE_RIGHT_X);
        assertThat(ArenaConstants.BRIDGE_START_Y).isLessThan(ArenaConstants.BRIDGE_END_Y);
        assertThat(ArenaConstants.RIVER_MIDLINE_Y)
                .isBetween(ArenaConstants.BRIDGE_START_Y, ArenaConstants.BRIDGE_END_Y);
    }

    @Test
    void player_field_is_below_river_and_bot_field_is_above() {
        assertThat(ArenaConstants.PLAYER_FIELD_MIN_Y)
                .isGreaterThanOrEqualTo(ArenaConstants.BRIDGE_END_Y);
        assertThat(ArenaConstants.BOT_FIELD_MAX_Y)
                .isLessThanOrEqualTo(ArenaConstants.BRIDGE_START_Y);
    }
}
