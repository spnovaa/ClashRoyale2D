package clashroyale.models;

import clashroyale.models.enums.BotKind;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OwnerTest {

    @Test
    void human_owner_is_not_a_bot() {
        Owner alice = Owner.human("alice");
        assertThat(alice.isHuman()).isTrue();
        assertThat(alice.isBot()).isFalse();
        assertThat(alice.id()).isEqualTo("alice");
        assertThat(alice.botKind()).isNull();
    }

    @Test
    void bot_owner_carries_kind() {
        Owner smart = Owner.bot(BotKind.SMART);
        assertThat(smart.isBot()).isTrue();
        assertThat(smart.botKind()).isEqualTo(BotKind.SMART);
        assertThat(smart.id()).isEqualTo(BotKind.SMART.legacyId());
    }

    @Test
    void value_equality_is_deep_not_referential() {
        assertThat(Owner.human("alice")).isEqualTo(Owner.human("alice"));
        assertThat(Owner.human("alice")).isNotEqualTo(Owner.human("bob"));
        assertThat(Owner.bot(BotKind.SMART)).isEqualTo(Owner.bot(BotKind.SMART));
        assertThat(Owner.bot(BotKind.SMART)).isNotEqualTo(Owner.bot(BotKind.SIMPLE));
    }

    @Test
    void fromLegacyId_recognises_known_bot_strings_and_falls_back_to_human() {
        assertThat(Owner.fromLegacyId("smartBot").isBot()).isTrue();
        assertThat(Owner.fromLegacyId("smartBot").botKind()).isEqualTo(BotKind.SMART);
        assertThat(Owner.fromLegacyId("simpleBot").botKind()).isEqualTo(BotKind.SIMPLE);
        assertThat(Owner.fromLegacyId("alice").isHuman()).isTrue();
    }

    @Test
    void opponent_check_is_symmetric_and_correct() {
        Owner alice = Owner.human("alice");
        Owner bot   = Owner.bot(BotKind.SMART);
        assertThat(alice.isOpponentOf(bot)).isTrue();
        assertThat(bot.isOpponentOf(alice)).isTrue();
        assertThat(alice.isOpponentOf(alice)).isFalse();
    }
}
