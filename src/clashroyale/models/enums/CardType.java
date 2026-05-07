package clashroyale.models.enums;

/**
 * Closed taxonomy of every card the game ships with.
 *
 * <p>Replaces the previous string-based identification — comparisons such
 * as {@code card.getTitle().equals("babyDragon")} were fragile, untyped,
 * and silently broke whenever a label was renamed.</p>
 *
 * <p>Each entry carries the legacy lowercase title (kept for FXML / DB
 * compatibility) and a thumbnail path so callers don't sprinkle these
 * literals throughout the codebase.</p>
 */
public enum CardType {

    // ─── Troops ─────────────────────────────────────────────────────────────
    ARCHER       (Category.TROOP,    "archer",       "/thumbCards/thumbarchers.png",       3),
    BARBARIAN    (Category.TROOP,    "barbarian",    "/thumbCards/thumbbarbarians.png",    5),
    BABY_DRAGON  (Category.TROOP,    "babyDragon",   "/thumbCards/thumbbabydragon.png",    4),
    GIANT        (Category.TROOP,    "giant",        "/thumbCards/thumbgiant.png",         5),
    MINI_PEKKA   (Category.TROOP,    "miniPEKKA",    "/thumbCards/thumbminipekka.png",     4),
    VALKYRIE     (Category.TROOP,    "valkyrie",     "/thumbCards/thumbvalkyrie.png",      4),
    WIZARD       (Category.TROOP,    "wizard",       "/thumbCards/thumbwizard.png",        5),

    // ─── Buildings ──────────────────────────────────────────────────────────
    CANNON       (Category.BUILDING, "cannon",       "/thumbCards/thumbcannon.png",        3),
    INFERNO_TOWER(Category.BUILDING, "infernoTower", "/thumbCards/thumbinfernotower.png",  5),

    // ─── Spells ─────────────────────────────────────────────────────────────
    FIREBALL     (Category.SPELL,    "fireball",     "/thumbCards/thumbfireball.png",      4),
    RAGE         (Category.SPELL,    "rage",         "/thumbCards/thumbrage.png",          3),
    ARROWS       (Category.SPELL,    "arrows",       "/thumbCards/thumbarrows.png",        3);

    /** Top-level category used for deck-composition rules and dispatch. */
    public enum Category { TROOP, BUILDING, SPELL }

    private final Category category;
    private final String legacyTitle;
    private final String thumbnailPath;
    private final int    cost;

    CardType(Category category, String legacyTitle, String thumbnailPath, int cost) {
        this.category      = category;
        this.legacyTitle   = legacyTitle;
        this.thumbnailPath = thumbnailPath;
        this.cost          = cost;
    }

    public Category category()      { return category; }
    public String   legacyTitle()   { return legacyTitle; }
    public String   thumbnailPath() { return thumbnailPath; }
    public int      cost()          { return cost; }

    /**
     * Reverse lookup by the legacy lowercase title used in the database
     * and FXML files. Returns {@code null} if no card matches — callers
     * should treat that as an explicit data-quality issue.
     */
    public static CardType fromLegacyTitle(String title) {
        if (title == null) return null;
        for (CardType type : values()) {
            if (type.legacyTitle.equals(title)) {
                return type;
            }
        }
        return null;
    }
}
