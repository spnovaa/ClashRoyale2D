package clashroyale.bots;

import clashroyale.bots.strategies.RandomDeploymentStrategy;
import clashroyale.bots.strategies.RandomFromHandSelectionStrategy;
import clashroyale.constants.ArenaConstants;
import clashroyale.models.cardsmodels.troops.Card;
import clashroyale.models.enums.BotKind;
import clashroyale.models.enums.CardType;
import clashroyale.models.factory.CardFactory;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BotPlayerTest {

    private static final String BOT_ID = BotKind.SIMPLE.legacyId();

    @Test
    void decide_returns_a_hold_when_the_selection_strategy_declines() {
        BotPlayer bot = new BotPlayer(
                BotKind.SIMPLE, 1,
                elixir -> null,                                  // never picks
                (card, ctx) -> Point2D.ZERO
        );
        BotPlayer.Move move = bot.decide(new BotContext(List.of(), BOT_ID));
        assertThat(move.isHold()).isTrue();
        assertThat(move.card()).isNull();
    }

    @Test
    void decide_combines_selection_and_deployment_strategies() {
        Card archer = CardFactory.create(CardType.ARCHER, 1, BOT_ID);
        BotPlayer bot = new BotPlayer(
                BotKind.SIMPLE, 1,
                elixir -> archer,
                (card, ctx) -> new Point2D(100, 100)
        );
        BotPlayer.Move move = bot.decide(new BotContext(List.of(), BOT_ID));
        assertThat(move.isHold()).isFalse();
        assertThat(move.card()).isSameAs(archer);
        assertThat(move.location()).isEqualTo(new Point2D(100, 100));
    }

    @Test
    void random_deployment_stays_inside_the_bots_field() {
        RandomDeploymentStrategy strategy = new RandomDeploymentStrategy(new Random(42L));
        for (int i = 0; i < 100; i++) {
            Point2D p = strategy.chooseLocation(null, new BotContext(List.of(), BOT_ID));
            assertThat(p.getX()).isBetween((double) ArenaConstants.BOT_FIELD_MIN_X,
                                           (double) ArenaConstants.BOT_FIELD_MAX_X);
            assertThat(p.getY()).isBetween((double) ArenaConstants.BOT_FIELD_MIN_Y,
                                           (double) ArenaConstants.BOT_FIELD_MAX_Y);
        }
    }

    @Test
    void random_hand_selection_only_returns_affordable_cards() {
        Card cheap     = CardFactory.create(CardType.ARROWS, 1, BOT_ID);     // cost 3
        Card expensive = CardFactory.create(CardType.GIANT, 1, BOT_ID);      // cost 5
        var hand = List.of(cheap, expensive);
        var strategy = new RandomFromHandSelectionStrategy(hand, new Random(0L));

        // With only 4 elixir, only the cheaper option is affordable —
        // running many trials we should sometimes pick null (when the
        // expensive one was rolled) but never violate the budget.
        for (int i = 0; i < 50; i++) {
            Card picked = strategy.chooseCard(4);
            if (picked != null) {
                assertThat(picked.getCost()).isLessThan(4);
            }
        }
    }

    @Test
    void elixir_management_clamps_and_validates() {
        BotPlayer bot = new BotPlayer(BotKind.SIMPLE, 1,
                elixir -> null, (c, ctx) -> Point2D.ZERO);
        int initial = bot.elixir();

        bot.addElixir(1);
        assertThat(bot.elixir()).isEqualTo(initial + 1);

        bot.spendElixir(2);
        assertThat(bot.elixir()).isEqualTo(initial - 1);

        assertThatThrownBy(() -> bot.spendElixir(999))
                .isInstanceOf(IllegalStateException.class);
    }
}
