package clashroyale.models.enums;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CardTypeTest {

    @Test
    void every_card_carries_a_unique_legacy_title() {
        long distinct = java.util.Arrays.stream(CardType.values())
                .map(CardType::legacyTitle)
                .distinct()
                .count();
        assertThat(distinct).isEqualTo(CardType.values().length);
    }

    @Test
    void fromLegacyTitle_round_trips_for_every_enum() {
        for (CardType type : CardType.values()) {
            assertThat(CardType.fromLegacyTitle(type.legacyTitle()))
                    .as("round-trip for %s", type)
                    .isEqualTo(type);
        }
    }

    @Test
    void fromLegacyTitle_returns_null_for_unknown_input() {
        assertThat(CardType.fromLegacyTitle("definitely-not-a-card")).isNull();
        assertThat(CardType.fromLegacyTitle(null)).isNull();
    }

    @Test
    void troops_buildings_and_spells_categories_are_populated() {
        assertThat(java.util.Arrays.stream(CardType.values())
                .filter(t -> t.category() == CardType.Category.TROOP)
                .count()).isGreaterThan(0);
        assertThat(java.util.Arrays.stream(CardType.values())
                .filter(t -> t.category() == CardType.Category.BUILDING)
                .count()).isGreaterThan(0);
        assertThat(java.util.Arrays.stream(CardType.values())
                .filter(t -> t.category() == CardType.Category.SPELL)
                .count()).isGreaterThan(0);
    }
}
