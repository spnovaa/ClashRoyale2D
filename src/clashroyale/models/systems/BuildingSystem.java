package clashroyale.models.systems;

import clashroyale.models.ArenaState;
import clashroyale.models.cardsmodels.buildings.Building;
import clashroyale.models.cardsmodels.buildings.Cannon;
import clashroyale.models.cardsmodels.buildings.InfernoTower;
import clashroyale.models.cardsmodels.troops.TroopsCard;
import clashroyale.models.towersmodels.KingTower;
import clashroyale.models.towersmodels.Tower;
import javafx.geometry.Point2D;

/**
 * Runs the per-tick lifecycle of every building on the arena:
 * <ol>
 *   <li>Attacks nearby enemies (type-specific logic per building).</li>
 *   <li>Decrements the building's remaining lifetime.</li>
 *   <li>Marks the building dead when its lifetime expires.</li>
 * </ol>
 */
public class BuildingSystem {

    private final ArenaState state;

    public BuildingSystem(ArenaState state) {
        this.state = state;
    }

    /** Process all buildings for one game tick. */
    public void tick() {
        for (Building building : state.buildings) {
            if (building.isAlive()) {
                if      (building instanceof InfernoTower it) processInfernoTower(it);
                else if (building instanceof Cannon       c)  processCannon(c);
            }
            building.decreaseLifeTime();
            if (building.getLifeTime() <= 0) building.setAlive(false);
        }
    }

    // ── InfernoTower ──────────────────────────────────────────────────────────

    private void processInfernoTower(InfernoTower tower) {
        Point2D pos   = posOf(tower);
        float   range = tower.getRange() * 10f;

        for (TroopsCard troop : state.troops) {
            if (!troop.getRelatedUser().equals(tower.getRelatedUser())
                    && distanceTo(pos, troop) <= range) {
                troop.setHp(troop.getHp() - tower.getDamage());
                if (troop.getHp() <= 0) troop.setAlive(false);
            }
        }
        for (Tower enemyTower : state.towers) {
            if (!enemyTower.getRelatedUser().equals(tower.getRelatedUser())
                    && distanceToTower(pos, enemyTower) <= range) {
                enemyTower.setHp(enemyTower.getHp() - tower.getDamage());
                if (enemyTower.getHp() <= 0) killTower(enemyTower);
            }
        }
        // Ramp damage up linearly over the building's lifetime
        int ramp = (tower.getMaxDamage() - tower.getMinDamage()) / tower.getFixLifeTime();
        tower.setDamage(tower.getDamage() + ramp);
    }

    // ── Cannon ────────────────────────────────────────────────────────────────

    private void processCannon(Cannon cannon) {
        Point2D pos   = posOf(cannon);
        float   range = cannon.getRange() * 10f;

        for (TroopsCard troop : state.troops) {
            if ("babyDragon".equals(troop.getTitle())) continue;   // Cannon can't hit air
            if (!troop.getRelatedUser().equals(cannon.getRelatedUser())
                    && distanceTo(pos, troop) <= range) {
                troop.setHp(troop.getHp() - cannon.getDamage());
                if (troop.getHp() <= 0) troop.setAlive(false);
            }
        }
        for (Tower enemyTower : state.towers) {
            if (!enemyTower.getRelatedUser().equals(cannon.getRelatedUser())
                    && distanceToTower(pos, enemyTower) <= range) {
                enemyTower.setHp(enemyTower.getHp() - cannon.getDamage());
                if (enemyTower.getHp() <= 0) killTower(enemyTower);
            }
        }
    }

    // ── Tower death ───────────────────────────────────────────────────────────

    private synchronized void killTower(Tower tower) {
        if (!tower.isAlive()) return;
        tower.setAlive(false);
        boolean botOwned = CombatSystem.isBot(tower.getRelatedUser());
        if (botOwned) {
            state.playerCrown = (tower instanceof KingTower) ? 3 : state.playerCrown + 1;
        } else {
            state.robotCrown  = (tower instanceof KingTower) ? 3 : state.robotCrown  + 1;
        }
    }

    // ── Utility ───────────────────────────────────────────────────────────────

    private Point2D posOf(Building b) {
        return new Point2D(b.getCenterPositionX(), b.getCenterPositionY());
    }

    private float distanceTo(Point2D origin, TroopsCard t) {
        return (float) origin.distance(t.getCenterPositionX(), t.getCenterPositionY());
    }

    private float distanceToTower(Point2D origin, Tower t) {
        return (float) origin.distance(t.getCenterPositionX(), t.getCenterPositionY());
    }
}
