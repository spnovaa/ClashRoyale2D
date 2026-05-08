package clashroyale.models;

import clashroyale.models.cardsmodels.buildings.Building;
import clashroyale.models.cardsmodels.spells.Spells;
import clashroyale.models.cardsmodels.troops.Card;
import clashroyale.models.cardsmodels.troops.TroopsCard;
import clashroyale.models.game.Robot;
import clashroyale.models.systems.BuildingSystem;
import clashroyale.models.systems.CombatSystem;
import clashroyale.models.systems.ElixirSystem;
import clashroyale.models.systems.MovementSystem;
import clashroyale.models.systems.SpellResolver;
import clashroyale.models.towersmodels.KingTower;
import clashroyale.models.towersmodels.QueenTower;
import clashroyale.models.towersmodels.Tower;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Orchestrates one game match by coordinating five focused game-systems.
 *
 * <p>All mutable arena state (entity lists, tower references, crowns, geometry)
 * is held in a single {@link ArenaState} instance shared among the systems.
 * {@code GameModel} itself contains no game logic — it delegates every
 * responsibility:</p>
 *
 * <table>
 *   <caption>System responsibilities</caption>
 *   <tr><th>System</th><th>Responsibility</th></tr>
 *   <tr><td>{@link CombatSystem}</td>  <td>Troop/tower attacks, crown accounting</td></tr>
 *   <tr><td>{@link MovementSystem}</td><td>Waypoint routing, step calculation, collision avoidance</td></tr>
 *   <tr><td>{@link SpellResolver}</td> <td>Spell effect application, spell lifetime expiry</td></tr>
 *   <tr><td>{@link BuildingSystem}</td><td>Building attacks, lifetime decay</td></tr>
 *   <tr><td>{@link ElixirSystem}</td>  <td>Elixir regeneration for both sides</td></tr>
 * </table>
 *
 * <p>The public getter/setter API is kept intentionally wide so that
 * {@code GameController} and {@code GameView} do not need to change.</p>
 */
public class GameModel {

    private static final Logger LOG = Logger.getLogger(GameModel.class.getName());

    // ── Shared state ──────────────────────────────────────────────────────────
    private final ArenaState state;

    // ── Game systems ──────────────────────────────────────────────────────────
    private final CombatSystem   combat;
    private final MovementSystem movement;
    private final SpellResolver  spellResolver;
    private final BuildingSystem buildings;
    private final ElixirSystem   elixir;

    // ── Match metadata ────────────────────────────────────────────────────────
    private final UserModel userModel;
    private final Robot     bot;
    private final int       userLevel;

    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Constructs and initialises a complete game match.
     *
     * @param userModel the human player's data
     * @param bot       the AI opponent
     */
    public GameModel(UserModel userModel, Robot bot) {
        this.userModel = userModel;
        this.bot       = bot;
        this.userLevel = userModel.getLevel();

        state = new ArenaState();
        initializeTowers(userModel.getUsername(), bot.getUsername(), userLevel);

        combat        = new CombatSystem(state);
        movement      = new MovementSystem(state, combat);
        spellResolver = new SpellResolver(state);
        buildings     = new BuildingSystem(state);
        elixir        = new ElixirSystem(userModel, bot);

        LOG.info(() -> "Match started: " + userModel.getUsername() + " vs " + bot.getUsername());
    }

    // ── Per-tick update entry points ──────────────────────────────────────────

    /** Advances all game entities by one tick. Called by the game timer. */
    public void updateGameModel() {
        // Troop movement / combat runs on the JavaFX thread to avoid concurrent
        // modification of the position data read by the renderer.
        Platform.runLater(() -> {
            for (TroopsCard card : state.troops) {
                if (!card.isAlive()) continue;
                boolean attacking = combat.attackNearestEnemyInRange(card);
                if (!attacking) movement.repositionCard(card);
            }
        });
        combat.processTowers();
        spellResolver.processExpiredSpells();
        buildings.tick();
    }

    /** Applies the effect of a deployed spell card immediately. */
    public void spellAction(Card spellCard) {
        spellResolver.resolve(spellCard);
    }

    /** Regenerates elixir for both sides if the regen threshold is reached. */
    public void updateElixirs() {
        elixir.tick();
    }

    // ── Score ─────────────────────────────────────────────────────────────────

    public int getPlayerCrown() { return state.playerCrown; }
    public int getRobotCrown()  { return state.robotCrown; }
    public void setPlayerCrown(int v) { state.playerCrown = v; }
    public void setRobotCrown(int v)  { state.robotCrown  = v; }

    // ── Entity-list accessors (used by GameController / GameView) ─────────────

    public ArrayList<TroopsCard> getArenaExistingTroops()     { return state.troops;    }
    public ArrayList<Spells>     getArenaExistingSpellCards()  { return state.spells;    }
    public ArrayList<Tower>      getArenaExistingTowers()      { return state.towers;    }
    public ArrayList<Building>   getArenaExistingBuildings()   { return state.buildings; }
    public ArrayList<Tower>      getArenaTowers()              { return state.towers;    }

    public void setArenaExistingTroops(ArrayList<TroopsCard> t)  { state.troops.clear();    state.troops.addAll(t);    }
    public void setArenaExistingSpellCards(ArrayList<Spells> s)  { state.spells.clear();    state.spells.addAll(s);    }
    public void setArenaExistingTowers(ArrayList<Tower> t)       { state.towers.clear();    state.towers.addAll(t);    }
    public void setArenaExistingBuildings(ArrayList<Building> b) { state.buildings.clear(); state.buildings.addAll(b); }

    // ── Tower accessors ───────────────────────────────────────────────────────

    public KingTower  getUserKingTower()        { return state.userKingTower; }
    public QueenTower getUserRightQueenTower()   { return state.userRightQueenTower; }
    public QueenTower getUserLeftQueenTower()    { return state.userLeftQueenTower; }
    public KingTower  getBotKingTower()         { return state.botKingTower; }
    public QueenTower getBotRightQueenTower()    { return state.botRightQueenTower; }
    public QueenTower getBotLeftQueenTower()     { return state.botLeftQueenTower; }

    public void setUserKingTower(KingTower t)         { state.userKingTower        = t; }
    public void setUserRightQueenTower(QueenTower t)  { state.userRightQueenTower  = t; }
    public void setUserLeftQueenTower(QueenTower t)   { state.userLeftQueenTower   = t; }
    public void setBotKingTower(KingTower t)          { state.botKingTower         = t; }
    public void setBotRightQueenTower(QueenTower t)   { state.botRightQueenTower   = t; }
    public void setBotLeftQueenTower(QueenTower t)    { state.botLeftQueenTower    = t; }

    // ── Geometry accessors ────────────────────────────────────────────────────

    public float getBridgesStartY() { return state.bridgesStartY; }
    public float getBridgesEndY()   { return state.bridgesEndY;   }
    public float getLeftBridgeX()   { return state.leftBridgeX;   }
    public float getRightBridgeX()  { return state.rightBridgeX;  }
    public float getBridgeWidth()   { return state.bridgesStartY - state.bridgesEndY; }
    public float getMinDist()       { return state.minDist;        }
    public float getUnitSize()      { return state.unitSize;       }

    // ── Misc accessors ────────────────────────────────────────────────────────

    public UserModel getUserModel()       { return userModel; }
    public void      setUserModel(UserModel m) { /* intentionally no-op; field is final */ }
    public Robot     getGameBot()         { return bot; }
    public Robot     getBot()             { return bot; }
    public int       getUserLevel()       { return userLevel; }
    public String    getPlayerOneUsername() { return userModel.getUsername(); }
    public String    getBotUsername()       { return bot.getUsername(); }
    public int       getPlayerLostHP()    { return 0; }   // legacy; HP-loss tracking is a future PR
    public int       getBotLostHP()       { return 0; }

    // ── Initialization ────────────────────────────────────────────────────────

    private void initializeTowers(String playerName, String botName, int level) {
        state.userKingTower       = new KingTower (level, playerName, 180, 420, 35, "userKingTower");
        state.userRightQueenTower = new QueenTower(level, playerName, 270, 375, 20, "userRightQueenTower");
        state.userLeftQueenTower  = new QueenTower(level, playerName,  85, 375, 20, "userLeftQueenTower");

        state.botKingTower        = new KingTower (level, botName,    180,  75, 35, "botKingTower");
        state.botRightQueenTower  = new QueenTower(level, botName,    270, 115, 20, "botRightQueenTower");
        state.botLeftQueenTower   = new QueenTower(level, botName,     85, 115, 20, "botLeftQueenTower");

        state.towers.add(state.userKingTower);
        state.towers.add(state.userRightQueenTower);
        state.towers.add(state.userLeftQueenTower);
        state.towers.add(state.botKingTower);
        state.towers.add(state.botRightQueenTower);
        state.towers.add(state.botLeftQueenTower);
    }
}
