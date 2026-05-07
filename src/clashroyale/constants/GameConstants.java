package clashroyale.constants;

import java.time.Duration;

/**
 * Game-rule and timing constants. Decoupled from arena geometry so
 * tweaking match length or elixir economy does not touch layout code.
 */
public final class GameConstants {

    private GameConstants() {
        // utility class
    }

    // ─── Frame loop ─────────────────────────────────────────────────────────
    public static final int FRAMES_PER_SECOND       = 15;
    public static final long FRAME_INTERVAL_MILLIS  = 1_000L / FRAMES_PER_SECOND;

    // ─── Match timing ───────────────────────────────────────────────────────
    public static final Duration MATCH_DURATION     = Duration.ofMinutes(3);

    // ─── Elixir economy ─────────────────────────────────────────────────────
    public static final int ELIXIR_INITIAL          = 4;
    public static final int ELIXIR_MAX              = 10;
    public static final int ELIXIR_REGEN_FRAMES     = 30;     // +1 elixir per N frames
    public static final int BOT_DECISION_INTERVAL_FRAMES = 15;

    // ─── Deck composition ───────────────────────────────────────────────────
    public static final int DECK_SIZE               = 8;
    public static final int SMART_BOT_TROOP_SLOTS   = 5;
    public static final int SMART_BOT_SPELL_SLOTS   = 2;
    public static final int SMART_BOT_BUILDING_SLOTS = 1;

    // ─── Combat tuning ──────────────────────────────────────────────────────
    /** Per-frame damage divisor (raw card damage / {@code DAMAGE_PER_FRAME_DIVISOR}). */
    public static final double DAMAGE_PER_FRAME_DIVISOR = 15.0;

    /** Multiplicative buff applied by the {@code Rage} spell. */
    public static final double RAGE_BUFF_MULTIPLIER     = 1.4;

    // ─── Crown tracking ─────────────────────────────────────────────────────
    public static final int CROWNS_TO_WIN          = 3;
}
