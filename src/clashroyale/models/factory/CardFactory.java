package clashroyale.models.factory;

import clashroyale.models.cardsmodels.buildings.Cannon;
import clashroyale.models.cardsmodels.buildings.InfernoTower;
import clashroyale.models.cardsmodels.spells.Arrows;
import clashroyale.models.cardsmodels.spells.Fireball;
import clashroyale.models.cardsmodels.spells.Rage;
import clashroyale.models.cardsmodels.troops.ArchersCard;
import clashroyale.models.cardsmodels.troops.BabyDragonCard;
import clashroyale.models.cardsmodels.troops.BarbariansCard;
import clashroyale.models.cardsmodels.troops.Card;
import clashroyale.models.cardsmodels.troops.GiantCard;
import clashroyale.models.cardsmodels.troops.MiniPEKKACard;
import clashroyale.models.cardsmodels.troops.ValkyrieCard;
import clashroyale.models.cardsmodels.troops.WizardCard;
import clashroyale.models.enums.CardType;

import java.util.ArrayList;
import java.util.List;

/**
 * Central, type-safe factory for every card the game ships with.
 *
 * <p>Eliminates the previous duplication where card lists were
 * hand-written in {@code UserModel.addAllCards()} and again in
 * {@code Robot.initializeAllCards()} — any new card type now needs
 * to be added in exactly one place.</p>
 *
 * <p>Switching on {@link CardType} (a closed set) lets the compiler
 * remind us when a new enum value is introduced.</p>
 */
public final class CardFactory {

    private CardFactory() {
        // utility class
    }

    /**
     * Build a single card of the given type at the supplied level for
     * the given owner.
     *
     * @throws IllegalArgumentException if the level is out of the
     *         supported range (1..5) or the card type is unknown.
     */
    public static Card create(CardType type, int level, String relatedUser) {
        validateLevel(level);
        return switch (type) {
            case ARCHER        -> new ArchersCard(level, relatedUser);
            case BARBARIAN     -> new BarbariansCard(level, relatedUser);
            case BABY_DRAGON   -> new BabyDragonCard(level, relatedUser);
            case GIANT         -> new GiantCard(level, relatedUser);
            case MINI_PEKKA    -> new MiniPEKKACard(level, relatedUser);
            case VALKYRIE      -> new ValkyrieCard(level, relatedUser);
            case WIZARD        -> new WizardCard(level, relatedUser);
            case CANNON        -> new Cannon(level, relatedUser);
            case INFERNO_TOWER -> new InfernoTower(level, relatedUser);
            case FIREBALL      -> new Fireball(level, relatedUser);
            case RAGE          -> new Rage(level, relatedUser);
            case ARROWS        -> new Arrows(level, relatedUser);
        };
    }

    /**
     * Lookup by the legacy lowercase title that lives in DB rows and
     * legacy {@code Card#getTitle()} calls.
     *
     * @throws IllegalArgumentException if the title doesn't match any card.
     */
    public static Card createByLegacyTitle(String legacyTitle, int level, String relatedUser) {
        CardType type = CardType.fromLegacyTitle(legacyTitle);
        if (type == null) {
            throw new IllegalArgumentException("Unknown card title: '" + legacyTitle + "'");
        }
        return create(type, level, relatedUser);
    }

    /**
     * Build one fresh instance of every card the game knows about,
     * useful for seeding a player's collection or a bot's pool.
     */
    public static List<Card> createAll(int level, String relatedUser) {
        List<Card> all = new ArrayList<>(CardType.values().length);
        for (CardType type : CardType.values()) {
            all.add(create(type, level, relatedUser));
        }
        return all;
    }

    private static void validateLevel(int level) {
        if (level < 1 || level > 5) {
            throw new IllegalArgumentException("Card level must be in 1..5 (was " + level + ")");
        }
    }
}
