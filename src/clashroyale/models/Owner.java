package clashroyale.models;

import clashroyale.models.enums.BotKind;

import java.util.Objects;

/**
 * Identifies the side an entity belongs to (a human user or a specific bot).
 *
 * <p>This is a lightweight value object that supersedes the
 * {@code String relatedUser} field carried on every card. Equality is
 * value-based so two references representing "smartBot" are
 * interchangeable; identity checks become typed and explicit.</p>
 *
 * <p>Construct via the static factories — never directly — to keep
 * legacy-string mapping in a single place.</p>
 */
public final class Owner {

    private final String id;
    private final boolean bot;
    private final BotKind botKind; // null when {@code bot} is false

    private Owner(String id, boolean bot, BotKind botKind) {
        this.id      = Objects.requireNonNull(id, "id");
        this.bot     = bot;
        this.botKind = botKind;
    }

    public static Owner human(String username) {
        return new Owner(username, false, null);
    }

    public static Owner bot(BotKind kind) {
        Objects.requireNonNull(kind, "kind");
        return new Owner(kind.legacyId(), true, kind);
    }

    /**
     * Reconstruct an {@link Owner} from the legacy free-form string
     * found on existing card instances and DB rows.
     */
    public static Owner fromLegacyId(String legacyId) {
        BotKind kind = BotKind.fromLegacyId(legacyId);
        return (kind != null) ? bot(kind) : human(legacyId);
    }

    public String  id()       { return id; }
    public boolean isBot()    { return bot; }
    public boolean isHuman()  { return !bot; }
    public BotKind botKind()  { return botKind; }

    public boolean isOpponentOf(Owner other) {
        return !this.equals(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Owner that)) return false;
        return bot == that.bot && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bot);
    }

    @Override
    public String toString() {
        return bot ? "Owner(bot=" + id + ")" : "Owner(human=" + id + ")";
    }
}
