package clashroyale.models.systems;

import clashroyale.constants.GameConstants;
import clashroyale.models.ArenaState;
import clashroyale.models.cardsmodels.buildings.Building;
import clashroyale.models.cardsmodels.spells.Arrows;
import clashroyale.models.cardsmodels.spells.Fireball;
import clashroyale.models.cardsmodels.spells.Rage;
import clashroyale.models.cardsmodels.spells.Spells;
import clashroyale.models.cardsmodels.troops.Card;
import clashroyale.models.cardsmodels.troops.TroopsCard;
import clashroyale.models.enums.Speed;
import clashroyale.models.towersmodels.KingTower;
import clashroyale.models.towersmodels.Tower;
import javafx.geometry.Point2D;

/**
 * Applies spell effects and manages spell lifetime expiry.
 *
 * <p>Two responsibilities, deliberately small:
 * <ol>
 *   <li>{@link #resolve(Card)} — immediately applies a newly deployed spell to
 *       every entity currently within its radius.</li>
 *   <li>{@link #processExpiredSpells()} — decrements spell timers each tick and
 *       marks expired spells as dead.</li>
 * </ol>
 * </p>
 */
public class SpellResolver {

    private final ArenaState state;

    public SpellResolver(ArenaState state) {
        this.state = state;
    }

    // ── Public entry points ───────────────────────────────────────────────────

    /** Decrements every active spell's timer; marks as dead when it expires. */
    public void processExpiredSpells() {
        for (Spells spell : state.spells) {
            if (spell.isAlive()) spell.decreaseTime();
            if (spell.getTime() <= 0) spell.setAlive(false);
        }
    }

    /** Immediately applies the effect of a newly deployed spell card. */
    public void resolve(Card spellCard) {
        Spells  spell        = castSpell(spellCard);
        Point2D origin       = new Point2D(spellCard.getCenterPositionX(), spellCard.getCenterPositionY());
        float   radiusPixels = spell.getRadious() * 10f;

        applyToTroops   (spell, origin, radiusPixels);
        applyToTowers   (spell, origin, radiusPixels);
        applyToBuildings(spell, origin, radiusPixels);
    }

    // ── Per-entity-type application ───────────────────────────────────────────

    private void applyToTroops(Spells spell, Point2D origin, float radius) {
        for (TroopsCard troop : state.troops) {
            if (!inRadius(origin, troop.getCenterPositionX(), troop.getCenterPositionY(), radius)) continue;
            if (spell instanceof Fireball fb) {
                if (!troop.getRelatedUser().equals(spell.getRelatedUser())) {
                    troop.setHp(troop.getHp() - fb.getAreaDamage());
                    if (troop.getHp() <= 0) troop.setAlive(false);
                }
            } else if (spell instanceof Rage) {
                if (troop.getRelatedUser().equals(spell.getRelatedUser())) applyRage(troop);
            } else if (spell instanceof Arrows) {
                if (!troop.getRelatedUser().equals(spell.getRelatedUser())) troop.setAlive(false);
            }
        }
    }

    private void applyToTowers(Spells spell, Point2D origin, float radius) {
        for (Tower tower : state.towers) {
            if (!inRadius(origin, tower.getCenterPositionX(), tower.getCenterPositionY(), radius)) continue;
            if (spell instanceof Fireball fb) {
                if (!tower.getRelatedUser().equals(spell.getRelatedUser())) {
                    tower.setHp(tower.getHp() - fb.getAreaDamage());
                    if (tower.getHp() <= 0) killTower(tower);
                }
            } else if (spell instanceof Rage) {
                if (tower.getRelatedUser().equals(spell.getRelatedUser())) {
                    tower.setDamage(tower.getDamage()     * GameConstants.RAGE_BUFF_MULTIPLIER);
                    tower.setHitSpeed(tower.getHitSpeed() * GameConstants.RAGE_BUFF_MULTIPLIER);
                }
            }
            // Arrows do not affect towers
        }
    }

    private void applyToBuildings(Spells spell, Point2D origin, float radius) {
        for (Building building : state.buildings) {
            if (!inRadius(origin, building.getCenterPositionX(), building.getCenterPositionY(), radius)) continue;
            if (spell instanceof Fireball fb) {
                if (!building.getRelatedUser().equals(spell.getRelatedUser())) {
                    building.setHp(building.getHp() - fb.getAreaDamage());
                    if (building.getHp() <= 0) building.setAlive(false);
                }
            } else if (spell instanceof Rage) {
                if (building.getRelatedUser().equals(spell.getRelatedUser())) {
                    building.setDamage(building.getDamage()     * GameConstants.RAGE_BUFF_MULTIPLIER);
                    building.setHitSpeed(building.getHitSpeed() * GameConstants.RAGE_BUFF_MULTIPLIER);
                }
            }
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void applyRage(TroopsCard troop) {
        troop.setDamage(troop.getDamage()     * GameConstants.RAGE_BUFF_MULTIPLIER);
        troop.setHitSpeed(troop.getHitSpeed() * GameConstants.RAGE_BUFF_MULTIPLIER);
        troop.setSpeed(switch (troop.getSpeed()) {
            case SLOW   -> Speed.SLOW_AMPLIFIED;
            case MEDIUM -> Speed.MEDIUM_AMPLIFIED;
            case FAST   -> Speed.FAST_AMPLIFIED;
            default     -> troop.getSpeed();
        });
    }

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

    private boolean inRadius(Point2D origin, float x, float y, float radius) {
        return (float) origin.distance(x, y) <= radius;
    }

    private Spells castSpell(Card card) {
        if (card instanceof Rage)     return (Rage)     card;
        if (card instanceof Arrows)   return (Arrows)   card;
        if (card instanceof Fireball) return (Fireball) card;
        throw new IllegalArgumentException("Unknown spell type: " + card.getClass().getSimpleName());
    }
}
