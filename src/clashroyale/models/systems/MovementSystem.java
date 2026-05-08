package clashroyale.models.systems;

import clashroyale.models.ArenaState;
import clashroyale.models.cardsmodels.troops.Card;
import clashroyale.models.cardsmodels.troops.TroopsCard;
import clashroyale.models.enums.Speed;
import clashroyale.models.enums.Target;
import clashroyale.models.towersmodels.Tower;
import javafx.geometry.Point2D;

/**
 * Resolves troop movement for a single game tick.
 *
 * <p>Responsibilities:
 * <ol>
 *   <li>Choose the correct waypoint (bridge entrance, tower, nearby invader).</li>
 *   <li>Advance the troop one speed-step toward that waypoint.</li>
 *   <li>Apply perpendicular displacement when troops block each other.</li>
 * </ol>
 * </p>
 *
 * <p>Range and speed look-ups delegate to {@link CombatSystem} so the pixel
 * conversion logic lives in exactly one place.</p>
 */
public class MovementSystem {

    private final ArenaState   state;
    private final CombatSystem combat;  // for rangePixels()

    public MovementSystem(ArenaState state, CombatSystem combat) {
        this.state  = state;
        this.combat = combat;
    }

    /** Moves a living troop one step toward its current waypoint. */
    public void repositionCard(TroopsCard card) {
        float   cardX   = card.getCenterPositionX();
        float   cardY   = card.getCenterPositionY();
        boolean isBot   = CombatSystem.isBot(card.getRelatedUser());
        boolean canFly  = "babyDragon".equals(card.getTitle());
        boolean isDown  = cardY > state.bridgesStartY;
        boolean isRight = cardX >= state.fieldCenterX;
        boolean isUp    = cardY < state.bridgesEndY;

        // Chase a nearby invader first
        Point2D invaderPos = findNearestInvader(card);
        if (invaderPos != null) {
            stepToTarget(card, (float) invaderPos.getX(), (float) invaderPos.getY(), true);
            return;
        }

        if (isDown && !canFly) {
            // Player's half — head toward the bridge
            moveFromPlayerHalf(card, isBot, isRight);
        } else if (isUp && !canFly) {
            // Enemy's half — head toward a tower
            moveFromEnemyHalf(card, isBot, isRight);
        } else {
            // Bridge zone — continue toward the appropriate tower
            Tower target = bridgeZoneTarget(isBot, isRight);
            if (target != null) stepToTarget(card, target.getCenterPositionX(), target.getCenterPositionY(), false);
        }
    }

    // ── Zone-specific routing ─────────────────────────────────────────────────

    private void moveFromPlayerHalf(TroopsCard card, boolean isBot, boolean isRight) {
        if (isRight) {
            if (!isBot) stepToTarget(card, state.rightBridgeX, state.bridgesStartY, true);
            else        stepToTower(card, isBot ? state.userRightQueenTower : null, state.userKingTower);
        } else {
            if (!isBot) stepToTarget(card, state.leftBridgeX, state.bridgesStartY, true);
            else        stepToTower(card, state.userLeftQueenTower, state.userKingTower);
        }
    }

    private void moveFromEnemyHalf(TroopsCard card, boolean isBot, boolean isRight) {
        if (isRight) {
            if (isBot) stepToTarget(card, state.rightBridgeX, state.bridgesEndY, true);
            else       stepToTower(card, state.botRightQueenTower, state.botKingTower);
        } else {
            if (isBot) stepToTarget(card, state.leftBridgeX, state.bridgesEndY, true);
            else       stepToTower(card, state.botLeftQueenTower, state.botKingTower);
        }
    }

    private Tower bridgeZoneTarget(boolean isBot, boolean isRight) {
        if (isBot) {
            return isRight
                    ? (state.userRightQueenTower.isAlive() ? state.userRightQueenTower : state.userKingTower)
                    : (state.userLeftQueenTower.isAlive()  ? state.userLeftQueenTower  : state.userKingTower);
        } else {
            return isRight
                    ? (state.botRightQueenTower.isAlive() ? state.botRightQueenTower : state.botKingTower)
                    : (state.botLeftQueenTower.isAlive()  ? state.botLeftQueenTower  : state.botKingTower);
        }
    }

    /** Steps toward {@code preferred} if it is alive, otherwise steps toward {@code fallback}. */
    private void stepToTower(TroopsCard card, Tower preferred, Tower fallback) {
        Tower target = (preferred != null && preferred.isAlive()) ? preferred : fallback;
        stepToTarget(card, target.getCenterPositionX(), target.getCenterPositionY(), false);
    }

    // ── Core movement ─────────────────────────────────────────────────────────

    private void stepToTarget(TroopsCard card, float targetX, float targetY, boolean passable) {
        float   cx    = card.getCenterPositionX();
        float   cy    = card.getCenterPositionY();
        float   range = combat.rangePixels(card.getRangeType());
        float   speed = speedPixels(card.getSpeed());
        Point2D cur   = new Point2D(cx, cy);
        Point2D tgt   = new Point2D(targetX, targetY);
        double  dist  = cur.distance(tgt);

        boolean shouldMove = passable ? dist > state.minDist : dist > range;
        if (!shouldMove) {
            if (passable) snapToPassableTarget(card, targetX, targetY);
            return;
        }

        double xRatio = (targetX - cx) / dist;
        double yRatio = (targetY - cy) / dist;
        float  stepX  = (float) (speed * xRatio);
        float  stepY  = (float) (speed * yRatio);

        avoidBlocking(card, speed, new Point2D(stepX, stepY));
        card.setCenterPositionX(card.getCenterPositionX() + stepX);
        card.setCenterPositionY(card.getCenterPositionY() + stepY);
    }

    private void snapToPassableTarget(TroopsCard card, float targetX, float targetY) {
        card.setCenterPositionX(targetX);
        card.setCenterPositionY(CombatSystem.isBot(card.getRelatedUser()) ? targetY + 2 : targetY - 1);
    }

    // ── Collision avoidance ───────────────────────────────────────────────────

    private void avoidBlocking(TroopsCard card, float speed, Point2D step) {
        for (Card other : state.troops) {
            if (other == card || !other.isAlive()) continue;
            Point2D p1 = new Point2D(card.getCenterPositionX(),  card.getCenterPositionY());
            Point2D p2 = new Point2D(other.getCenterPositionX(), other.getCenterPositionY());
            if (p1.distance(p2) < speed + state.unitSize) {
                applyPerpendicular(card, other, step);
            } else {
                card.setBeingBlocked(false);
            }
        }
    }

    private void applyPerpendicular(Card card, Card other, Point2D step) {
        float perpX = (float) (-step.getY());
        float perpY = (float) step.getX();
        if ((!card.isBeingBlocked() && !other.isBeingBlocked())
                || (card.isBeingBlocked() && !other.isBeingBlocked())) {
            card.setBeingBlocked(true);
            card.setCenterPositionX(card.getCenterPositionX()   + perpX);
            card.setCenterPositionY(card.getCenterPositionY()   + perpY);
        } else if (!card.isBeingBlocked()) {
            other.setCenterPositionX(other.getCenterPositionX() + perpX);
            other.setCenterPositionY(other.getCenterPositionY() + perpY);
        }
    }

    // ── Invader detection ─────────────────────────────────────────────────────

    private static final float INVADER_DETECTION_RADIUS = 70f;

    private Point2D findNearestInvader(TroopsCard card) {
        if (card.getTarget() == Target.BUILDINGS) return null;
        Point2D cardPos = new Point2D(card.getCenterPositionX(), card.getCenterPositionY());
        for (Card other : state.troops) {
            if (!other.isAlive()) continue;
            if (other.getRelatedUser().equals(card.getRelatedUser())) continue;
            if (!canTargetUnit(card, other)) continue;
            Point2D pos = new Point2D(other.getCenterPositionX(), other.getCenterPositionY());
            if (pos.distance(cardPos) < INVADER_DETECTION_RADIUS) return pos;
        }
        return null;
    }

    private boolean canTargetUnit(TroopsCard attacker, Card target) {
        return !("babyDragon".equals(target.getTitle())
                && (attacker.getTarget() == Target.GROUND
                    || attacker.getTarget() == Target.BUILDINGS));
    }

    // ── Speed look-up ─────────────────────────────────────────────────────────

    private float speedPixels(Speed speed) {
        return switch (speed) {
            case SLOW             -> state.minDist;
            case MEDIUM           -> 2f;
            case FAST             -> 3f;
            case SLOW_AMPLIFIED   -> state.minDist * 1.4f;
            case MEDIUM_AMPLIFIED -> 2f * 1.4f;
            case FAST_AMPLIFIED   -> 3f * 1.4f;
            default               -> 1f;
        };
    }
}
