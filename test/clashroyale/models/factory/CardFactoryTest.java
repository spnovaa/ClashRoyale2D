package clashroyale.models.factory;

import clashroyale.models.cardsmodels.troops.Card;
import clashroyale.models.enums.CardType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CardFactoryTest {

    private static final String OWNER = "tester";

    @Test
    void create_returns_a_card_for_every_known_type_at_every_level() {
        for (CardType type : CardType.values()) {
            for (int level = 1; level <= 5; level++) {
                Card card = CardFactory.create(type, level, OWNER);
                assertThat(card).as("create(%s, %d)", type, level).isNotNull();
                assertThat(card.getRelatedUser()).isEqualTo(OWNER);
            }
        }
    }

    @Test
    void create_rejects_levels_outside_one_through_five() {
        assertThatThrownBy(() -> CardFactory.create(CardType.GIANT, 0, OWNER))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> CardFactory.create(CardType.GIANT, 6, OWNER))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createByLegacyTitle_handles_known_titles_and_rejects_unknown_ones() {
        assertThat(CardFactory.createByLegacyTitle("giant", 1, OWNER)).isNotNull();
        assertThatThrownBy(() -> CardFactory.createByLegacyTitle("dragonking-9000", 1, OWNER))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createAll_returns_one_of_each() {
        List<Card> all = CardFactory.createAll(1, OWNER);
        assertThat(all).hasSize(CardType.values().length);
    }
}
