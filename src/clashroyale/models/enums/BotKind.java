package clashroyale.models.enums;

/**
 * The kind of opponent the player is facing.
 *
 * <p>Replaces the previous string contracts ({@code "simpleBot"},
 * {@code "smartBot"}) with a closed type that is safe to switch on,
 * persist, and compare.</p>
 */
public enum BotKind {

    SIMPLE("simpleBot", 1),
    SMART ("smartBot",  2);

    private final String legacyId;
    private final int    legacyDbValue;

    BotKind(String legacyId, int legacyDbValue) {
        this.legacyId      = legacyId;
        this.legacyDbValue = legacyDbValue;
    }

    public String legacyId()       { return legacyId; }
    public int    legacyDbValue()  { return legacyDbValue; }

    public static BotKind fromLegacyDbValue(int value) {
        for (BotKind k : values()) {
            if (k.legacyDbValue == value) return k;
        }
        return SIMPLE;
    }

    public static BotKind fromLegacyId(String id) {
        if (id == null) return null;
        for (BotKind k : values()) {
            if (k.legacyId.equals(id)) return k;
        }
        return null;
    }
}
