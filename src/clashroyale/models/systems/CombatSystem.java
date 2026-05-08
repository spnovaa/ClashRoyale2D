package clashroyale.models.systems;

import clashroyale.models.ArenaState;
import clashroyale.models.cardsmodels.buildings.Building;
import clashroyale.models.cardsmodels.troops.Card;
import clashroyale.models.cardsmodels.troops.TroopsCard;
import clashroyale.models.enums.Range;
import clashroyale.models.enums.Target;
import clashroyale.models.towersmodels.KingTower;
import clashroyale.models.towersmodels.Tower;
import javafx.application.Platform;
import javafx.geometry.Point2D;

import java.util.logging.Logger;

/**
 * Handles all combat interactions for one game tick:
 * <ul>
 *   <li>Troop attacks (troop-to-troop, troop-to-tower, troop-to-building)</li>
 *   <li>Tower attacks (tower-to-troop, tower-to-building)</li>
 *   <li>Crown accounting when a tower dies</li>
 * </ul>
 *
 * <p>All state lives in the shared {@link ArenaState}; this class is stateless
 * beyond its reference to that state.</p>
 */
public class CombatSystem {

    private static final Logger LOG = Logger.getLogger(CombatSystem.class.getName());

    private final ArenaState state;

    public CombatSystem(ArenaState state) {
        this.state = state;
    }

    // ── Public entry points called by GameModel ───────────────────────────────

    /** Runs attack logic for every living tower. */
    public void processTowers() {
        processTowerAttack(state.userRightQueenTower);
        processTowerAttack(state.userLeftQueenTower);
        processTowerAttack(state.userKingTower);
        processTowerAttack(state.botRightQueenTower);
        processTowerAttack(state.botLeftQueenTower);
        processTowerAttack(state.botKingTower);
    }

    /**
     * Attempts to attack the nearest enemy within range.
     *
     * @return {@code true} if the card found a target and is attacking this tick
     */
    public boolean attackNearestEnemyInRange(Card card) {
        float distToTower = Float.MAX_VALUE;
        float distToCard  = Float.MAX_VALUE;

        Card  enemyCard  = findNearestEnemyCardInRange(card);
        Tower enemyTower = findNearestEnemyTowerInRange(card);

        Point2D cardPos = posOf(card);

        if (enemyCard  != null && enemyCard.isAlive())   distToCard  = (float) posOf(enemyCard).distance(cardPos);
        if (enemyTower != null && enemyTower.isAlive())  distToTower = (float) posOfTower(enemyTower).distance(cardPos);

        boolean isAttacking = (enemyCard != null || enemyTower != null);
        float   range       = rangePixels(((TroopsCard) card).getRangeType());

        if (distToTower < distToCard && distToTower <= range && enemyTower != null) {
            Tower finalTower = enemyTower;
            Platform.runLater(() -> attackCardToTower(card, finalTower));
        } else if (distToCard <= range && enemyCard != null) {
            Card finalCard = enemyCard;
            Platform.runLater(() -> attackCardToCard(card, finalCard));
        }
        return isAttacking;
    }

    // ── Tower attack logic ────────────────────────────────────────────────────

    private void processTowerAttack(Tower tower) {
        if (!tower.isAlive()) return;
        Card enemy = findNearestTowerEnemy(tower);
        if (tower.getTarget() == null) tower.setTarget(enemy);
        attackTowerToCard(tower, enemy);
        if (enemy instanceof TroopsCard tc && tc.getHp() < 0) {
            tc.setAlive(false);
            tower.setTarget(null);
        }
    }

    private Card findNearestTowerEnemy(Tower tower) {
        Card  nearest  = null;
        float leastDist = 8_000f;
        Point2D towerPos = posOfTower(tower);
        for (TroopsCard card : state.troops) {
            if (card.isAlive() && !card.getRelatedUser().equals(tower.getRelatedUser())) {
                float d = (float) towerPos.distance(posOf(card));
                if (d < leastDist && d <= tower.getRange() * 10f) {
                    nearest   = card;
                    leastDist = d;
                }
            }
        }
        return nearest;
    }

    private void attackTowerToCard(Tower tower, Card target) {
        if (!tower.isAlive() || target == null) return;
        if (target instanceof TroopsCard tc) {
            tc.setHp(tc.getHp() - tower.getDamage());
            if (tc.getHp() < 0) {
                tc.setAlive(false);
                tower.setTarget(null);
            }
        } else if (target instanceof Building b) {
            b.setHp(b.getHp() - tower.getDamage());
            if (b.getHp() < 0) {
                b.setAlive(false);
                tower.setTarget(null);
            }
        }
    }

    // ── Card attack logic ─────────────────────────────────────────────────────

    private Tower findNearestEnemyTowerInRange(Card card) {
        boolean isBot = isBot(card.getRelatedUser());
        Tower[] candidates = isBot
                ? new Tower[]{state.userKingTower, state.userRightQueenTower, state.userLeftQueenTower}
                : new Tower[]{state.botKingTower,  state.botRightQueenTower,  state.botLeftQueenTower};

        Point2D cardPos = posOf(card);
        float   range   = rangePixels(((TroopsCard) card).getRangeType());
        Tower   nearest = null;
        float   minD    = Float.MAX_VALUE;
        for (Tower t : candidates) {
            float d = (float) cardPos.distance(posOfTower(t));
            if (d < minD) { minD = d; nearest = t; }
        }
        return (minD <= range) ? nearest : null;
    }

    private Card findNearestEnemyCardInRange(Card card) {
        float leastDist;
        if      (card instanceof TroopsCard tc) leastDist = rangePixels(tc.getRangeType());
        else if (card instanceof Building   b)  leastDist = (float) b.getRange();
        else                                    leastDist = 10_000f;

        Card    nearest  = null;
        boolean isGiant  = "giant".equals(card.getTitle());

        for (TroopsCard tc : state.troops) {
            if (tc.isAlive() && !tc.getRelatedUser().equals(card.getRelatedUser()) && !isGiant) {
                float d = distance(card, tc);
                if (d < leastDist) { nearest = tc; leastDist = d; }
            }
        }
        for (Building b : state.buildings) {
            if (b.isAlive() && !b.getRelatedUser().equals(card.getRelatedUser())) {
                float d = distance(card, b);
                if (d < leastDist) { nearest = b; leastDist = d; }
            }
        }
        return nearest;
    }

    private void attackCardToTower(Card attacker, Tower target) {
        double newHp = target.getHp() - damageOf(attacker);
        if (newHp >= 0) {
            target.setHp(newHp);
        } else {
            killTower(target);
        }
    }

    private void attackCardToCard(Card attacker, Card target) {
        if (target instanceof TroopsCard tc) {
            boolean allowed = !"babyDragon".equals(target.getTitle())
                    || (attacker instanceof TroopsCard atc
                        && (atc.getTarget() == Target.AIR || atc.getTarget() == Target.AIR_GROUND));
            if (!allowed) return;
            double dmg = damageOf(attacker);
            tc.setHp(tc.getHp() - dmg);
            if (tc.getHp() < 0) tc.setAlive(false);
        } else if (target instanceof Building tb) {
            double dmg = damageOf(attacker);
            tb.setHp(tb.getHp() - dmg);
            if (tb.getHp() < 0) tb.setAlive(false);
        }
    }

    // ── Tower death & crown accounting ────────────────────────────────────────

    synchronized void killTower(Tower tower) {
        if (!tower.isAlive()) return;
        tower.setAlive(false);
        LOG.info(() -> tower.getRelatedUser() + " lost " + tower.getTitle());
        boolean botOwned = isBot(tower.getRelatedUser());
        if (botOwned) {
            state.playerCrown = (tower instanceof KingTower) ? 3 : state.playerCrown + 1;
        } else {
            state.robotCrown  = (tower instanceof KingTower) ? 3 : state.robotCrown  + 1;
        }
    }

    // ── Utility ───────────────────────────────────────────────────────────────

    /**
     * Converts a {@link Range} category to pixel distance.
     * Used by both {@link CombatSystem} and {@link MovementSystem}.
     */
    public float rangePixels(Range range) {
        return switch (range) {
            case RANGED3 -> 30f;
            case RANGED5 -> 50f;
            case RANGED6 -> 60f;
            default      -> 10f;
        };
    }

    private double damageOf(Card card) {
        if (card instanceof TroopsCard tc) return tc.getDamage();
        if (card instanceof Building   b)  return b.getDamage();
        return 0.0;
    }

    private float distance(Card a, Card b) {
        return (float) posOf(a).distance(posOf(b));
    }

    private Point2D posOf(Card c) {
        return new Point2D(c.getCenterPositionX(), c.getCenterPositionY());
    }

    private Point2D posOfTower(Tower t) {
        return new Point2D(t.getCenterPositionX(), t.getCenterPositionY());
    }

    static boolean isBot(String user) {
        return "simpleBot".equals(user) || "smartBot".equals(user);
    }
}
