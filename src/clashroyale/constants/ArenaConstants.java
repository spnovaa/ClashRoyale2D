package clashroyale.constants;

/**
 * Arena geometry — every coordinate, bridge boundary, and quadrant edge
 * that used to live as a magic number scattered across {@code GameModel},
 * {@code SmartRobot} and {@code GameView} now lives here.
 *
 * <p>Centralising these values makes the arena layout adjustable in
 * one place and turns implicit assumptions into named, documented
 * constants.</p>
 */
public final class ArenaConstants {

    private ArenaConstants() {
        // utility class
    }

    // ─── Arena bounds (pixels) ──────────────────────────────────────────────
    public static final int ARENA_WIDTH        = 340;
    public static final int ARENA_HEIGHT       = 450;
    public static final int ARENA_LEFT_EDGE    = 25;
    public static final int ARENA_RIGHT_EDGE   = 340;
    public static final int ARENA_HORIZONTAL_MIDPOINT = 183;

    // ─── Bridge geometry ────────────────────────────────────────────────────
    public static final int BRIDGE_LEFT_X      = 85;
    public static final int BRIDGE_RIGHT_X     = 275;
    public static final int BRIDGE_START_Y     = 230;
    public static final int BRIDGE_END_Y       = 260;
    public static final int RIVER_MIDLINE_Y    = 240;

    // ─── Field divisions ────────────────────────────────────────────────────
    /** Bot's playable field is from minX..maxX, minY..maxY. */
    public static final int BOT_FIELD_MIN_X    = ARENA_LEFT_EDGE;
    public static final int BOT_FIELD_MAX_X    = ARENA_RIGHT_EDGE;
    public static final int BOT_FIELD_MIN_Y    = 35;
    public static final int BOT_FIELD_MAX_Y    = BRIDGE_START_Y;

    public static final int PLAYER_FIELD_MIN_Y = BRIDGE_END_Y;
    public static final int PLAYER_FIELD_MAX_Y = ARENA_HEIGHT;

    // ─── Quadrant boundaries used by the smart-bot deployment heuristic ─────
    /** Y boundary between the two upper quadrants (1, 2) and the two middle quadrants (3, 4). */
    public static final int QUADRANT_TOP_DIVIDER_Y    = 133;
    /** Y boundary between the two lower quadrants (5, 6) and the deepest quadrants (7, 8). */
    public static final int QUADRANT_BOTTOM_DIVIDER_Y = 340;

    /** Range value (in arena units) multiplied by this to get pixel range. */
    public static final int RANGE_PIXELS_PER_UNIT = 10;
}
