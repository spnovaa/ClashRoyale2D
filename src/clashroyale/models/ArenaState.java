package clashroyale.models;

import clashroyale.models.cardsmodels.buildings.Building;
import clashroyale.models.cardsmodels.spells.Spells;
import clashroyale.models.cardsmodels.troops.TroopsCard;
import clashroyale.models.towersmodels.KingTower;
import clashroyale.models.towersmodels.QueenTower;
import clashroyale.models.towersmodels.Tower;

import java.util.ArrayList;

/**
 * Shared, mutable snapshot of all live entities and arena geometry for one match.
 *
 * <p>Each game-system (combat, movement, spells, buildings, elixir) holds a
 * reference to the same {@code ArenaState} instance so they all read and write
 * the same data without coupling to the top-level {@link GameModel}.</p>
 *
 * <p>Arena geometry fields are {@code final} after construction; entity lists and
 * crown counters are mutable and updated by the systems on every tick.</p>
 */
public class ArenaState {

    // ── Entity collections ────────────────────────────────────────────────────
    public final ArrayList<TroopsCard> troops    = new ArrayList<>();
    public final ArrayList<Spells>     spells    = new ArrayList<>();
    public final ArrayList<Tower>      towers    = new ArrayList<>();
    public final ArrayList<Building>   buildings = new ArrayList<>();

    // ── Tower references ──────────────────────────────────────────────────────
    public KingTower  userKingTower;
    public QueenTower userRightQueenTower;
    public QueenTower userLeftQueenTower;

    public KingTower  botKingTower;
    public QueenTower botRightQueenTower;
    public QueenTower botLeftQueenTower;

    // ── Score counters (volatile: written on timer thread, read on FX thread) ─
    public volatile int playerCrown;
    public volatile int robotCrown;

    // ── Arena geometry ────────────────────────────────────────────────────────
    public final float fieldCenterX  = 190f;
    public final float bridgesStartY = 260f;  // bottom edge of bridge zone
    public final float bridgesEndY   = 230f;  // top edge of bridge zone
    public final float leftBridgeX   =  85f;
    public final float rightBridgeX  = 275f;
    public final float minDist       =   1f;
    public final float unitSize      =  20f;
}
