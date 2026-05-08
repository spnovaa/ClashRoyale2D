package clashroyale.views;

import clashroyale.models.UserModel;
import clashroyale.models.cardsmodels.buildings.Building;
import clashroyale.models.cardsmodels.spells.Spells;
import clashroyale.models.cardsmodels.troops.Card;
import clashroyale.models.cardsmodels.troops.TroopsCard;
import clashroyale.models.game.LeftTime;
import clashroyale.models.towersmodels.Tower;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


/**
 * Renders the game arena and card queue on the JavaFX scene graph.
 *
 * <p>Responsibilities:
 * <ul>
 *   <li>Display the player's card hand and cycle cards after deployment.</li>
 *   <li>Add / remove / reposition {@link ImageView}s for every live entity.</li>
 *   <li>Update HUD labels (timer, crowns, elixir).</li>
 * </ul>
 * </p>
 *
 * <p>The two formerly-duplicate {@code deployBotClick} / {@code deployUserClick}
 * methods have been merged into a single {@link #deployCard} helper.
 * Image look-up and layout are each handled by a dedicated private method so
 * they can be changed independently.</p>
 */
public class GameView extends Group {

    // ── Constants ─────────────────────────────────────────────────────────────
    private static final int TROOPS_SIZE = 60;

    // ── Model reference ───────────────────────────────────────────────────────
    private final UserModel userModel;

    // ── HUD labels ────────────────────────────────────────────────────────────
    private Label textTime;
    private Label botCrown;
    private Label playerCrown;
    private Label userElixir;
    private Label botElixir;

    // ── Card-queue image views ────────────────────────────────────────────────
    private ImageView displayedCard1;
    private ImageView displayedCard2;
    private ImageView displayedCard3;
    private ImageView displayedCard4;
    private ImageView nextCard;

    // ── Tower image views ─────────────────────────────────────────────────────
    private ImageView botKing;
    private ImageView botLeftQueen;
    private ImageView botRightQueen;
    private ImageView userKing;
    private ImageView userLeftQueen;
    private ImageView userRightQueen;

    // ── Arena state ───────────────────────────────────────────────────────────
    private AnchorPane         anchorPane;
    private Queue<Card>        cardsQue;
    private ArrayList<ImageView> battleCards;
    private ArrayList<Tower>   arenaTowers;

    // ── Misc ──────────────────────────────────────────────────────────────────
    private LeftTime leftTime;
    private int      timerFrames;
    private int      chosenCardIndex;

    // ─────────────────────────────────────────────────────────────────────────

    public GameView(UserModel userModel) {
        this.userModel   = userModel;
        this.leftTime    = new LeftTime();
        this.battleCards = new ArrayList<>();
    }

    // ── Setup / wiring ────────────────────────────────────────────────────────

    public void instantiateCardsQueImageViews(ImageView card1, ImageView card2,
                                              ImageView card3, ImageView card4,
                                              ImageView next) {
        this.displayedCard1 = card1;
        this.displayedCard2 = card2;
        this.displayedCard3 = card3;
        this.displayedCard4 = card4;
        this.nextCard       = next;
    }

    public void instantiateTowers(ImageView botKing, ImageView botLeftQueen, ImageView botRightQueen,
                                  ImageView userKing, ImageView userLeftQueen, ImageView userRightQueen) {
        this.botKing      = botKing;
        this.botLeftQueen = botLeftQueen;
        this.botRightQueen= botRightQueen;
        this.userKing     = userKing;
        this.userLeftQueen= userLeftQueen;
        this.userRightQueen=userRightQueen;
        this.arenaTowers  = new ArrayList<>();
    }

    public void setAnchorPane(AnchorPane anchorPane) { this.anchorPane = anchorPane; }

    /** Populates the initial 4-card hand and the "next" slot from the deck. */
    public void prepareArena() {
        cardsQue = new LinkedList<>(userModel.getChosenCardsList());

        ImageView[] slots = {displayedCard1, displayedCard2, displayedCard3, displayedCard4};
        for (ImageView slot : slots) {
            Card card = cardsQue.poll();
            if (card != null) {
                slot.setImage(cardQueueImage(card.getTitle()));
                slot.setUserData(card);
            }
        }
        Card next = cardsQue.poll();
        if (next != null) {
            nextCard.setImage(cardQueueImage(next.getTitle()));
            nextCard.setUserData(next);
        }
    }

    // ── Card deployment ───────────────────────────────────────────────────────

    /**
     * Adds an {@link ImageView} for the deployed card to the arena.
     * User deployments additionally rotate the card queue.
     */
    public void deployTroops(float x, float y, Card card) {
        boolean isUser = !card.getRelatedUser().equals("simpleBot")
                      && !card.getRelatedUser().equals("smartBot");
        deployCard(x, y, card, isUser);
    }

    private void deployCard(float x, float y, Card card, boolean isUser) {
        Image     image = unitImage(card.getTitle(), isUser);
        ImageView iv    = new ImageView(image);
        applyLayout(iv, card.getTitle(), x, y, isUser);
        iv.setUserData(card);
        anchorPane.getChildren().add(iv);
        battleCards.add(iv);
        if (isUser) rotateCardQueue();
    }

    // ── Image loading ─────────────────────────────────────────────────────────

    /**
     * Returns the unit sprite for a card.
     * Troops show a different animation frame depending on which side deployed them.
     */
    private Image unitImage(String title, boolean isUser) {
        String path = switch (title) {
            case "cannon"       -> "chr/cannon/cannon1.png";
            case "infernoTower" -> "chr/inferno/building_inferno_tower_sprite_2.png";
            case "arrows"       -> "Ski_trail_rating_symbol_red_circle.png";
            case "fireball"     -> "png-transparent-computer-icons-circle-circle-orange-sphere-desktop-wallpaper-thumbnail.png";
            case "rage"         -> "Pan_Blue_Circle.png";
            case "archer"       -> isUser ? "chr/archer/chr_archer_sprite_069.png"
                                          : "chr/archer/chr_archer_sprite_000.png";
            case "babyDragon"   -> isUser ? "chr/babydragon/chr_baby_dragon_sprite_111.png"
                                          : "chr/babydragon/chr_baby_dragon_sprite_003.png";
            case "barbarian"    -> isUser ? "chr/barbarian/chr_barbarian_sprite_0251.png"
                                          : "chr/barbarian/chr_barbarian_sprite_0252.png";
            case "giant"        -> isUser ? "chr/giant/chr_giant_sprite_134.png"
                                          : "chr/giant/chr_giant_sprite_000.png";
            case "miniPEKKA"    -> isUser ? "chr/minipekka/chr_mini_pekka_sprite_104.png"
                                          : "chr/minipekka/chr_mini_pekka_sprite_002.png";
            case "valkyrie"     -> isUser ? "chr/valkyrie/chr_valkyrie_sprite_060.png"
                                          : "chr/valkyrie/chr_valkyrie_sprite_004.png";
            case "wizard"       -> isUser ? "chr/wizard/chr_wizard_sprite_070.png"
                                          : "chr/wizard/chr_wizard_sprite_004.png";
            default             -> "ui/0.png";
        };
        return new Image(getClass().getResourceAsStream("../resources/" + path));
    }

    /**
     * Returns the thumbnail image shown in the card-queue HUD slots.
     */
    private Image cardQueueImage(String title) {
        String path = switch (title) {
            case "cannon"       -> "cardsWithLabel/cannon.png";
            case "infernoTower" -> "cardsWithLabel/inferno-tower.png";
            case "arrows"       -> "cardsWithLabel/arrows.png";
            case "fireball"     -> "thumbCards/thumbfireball.png";
            case "rage"         -> "cardsWithLabel/rage.png";
            case "archer"       -> "cardsWithLabel/archers.png";
            case "babyDragon"   -> "cardsWithLabel/baby-dragon.png";
            case "barbarian"    -> "cardsWithLabel/barbarians.png";
            case "giant"        -> "cardsWithLabel/giant.png";
            case "miniPEKKA"    -> "cardsWithLabel/mini-pekka.png";
            case "valkyrie"     -> "cardsWithLabel/valkyrie.png";
            case "wizard"       -> "cardsWithLabel/wizard.png";
            default             -> null;
        };
        return path != null ? new Image(getClass().getResourceAsStream("../resources/" + path)) : null;
    }

    // ── Layout sizing ─────────────────────────────────────────────────────────

    /**
     * Positions and sizes an {@link ImageView} according to card type.
     * Bot buildings are rendered at half size to give visual distinction.
     */
    private void applyLayout(ImageView iv, String title, float x, float y, boolean isUser) {
        switch (title) {
            case "rage" -> {
                iv.setOpacity(0.3);
                iv.setX(x - 25); iv.setY(y - 25);
                iv.setFitWidth(50); iv.setFitHeight(50);
            }
            case "fireball" -> {
                iv.setOpacity(0.3);
                iv.setX(x - 12.5f); iv.setY(y - 12.5f);
                iv.setFitWidth(25); iv.setFitHeight(25);
            }
            case "arrows" -> {
                iv.setOpacity(0.3);
                iv.setX(x - 20); iv.setY(y - 20);
                iv.setFitWidth(40); iv.setFitHeight(40);
            }
            case "cannon", "infernoTower" -> {
                // Bot buildings rendered at half scale
                float scale = isUser ? 0.5f : 0.25f;
                float size  = TROOPS_SIZE * scale;
                iv.setX(x - size / 2); iv.setY(y - size / 2);
                iv.setFitWidth(size);  iv.setFitHeight(size);
            }
            default -> {
                iv.setX(x - (float) TROOPS_SIZE / 2);
                iv.setY(y - (float) TROOPS_SIZE / 2);
                iv.setFitWidth(TROOPS_SIZE);
                iv.setFitHeight(TROOPS_SIZE);
            }
        }
    }

    // ── Card-queue rotation ───────────────────────────────────────────────────

    /** Replaces the deployed card's slot with the "next" card and pulls a new next from the queue. */
    private void rotateCardQueue() {
        Card deployed = userModel.getChosenToDeployCard();
        if (deployed == null) return;

        ImageView[] slots = {displayedCard1, displayedCard2, displayedCard3, displayedCard4};
        for (ImageView slot : slots) {
            Card slotCard = (Card) slot.getUserData();
            if (slotCard != null && slotCard.getTitle().equals(deployed.getTitle())) {
                slot.setImage(cardQueueImage(((Card) nextCard.getUserData()).getTitle()));
                slot.setUserData(nextCard.getUserData());
                break;
            }
        }

        Card newNext = cardsQue.poll();
        if (newNext != null) {
            nextCard.setUserData(newNext);
            nextCard.setImage(cardQueueImage(newNext.getTitle()));
            cardsQue.add(deployed);
        }
        userModel.setChosenToDeployCard(null);
    }

    // ── Per-tick HUD updates ──────────────────────────────────────────────────

    public void updateTimer() {
        timerFrames++;
        if (timerFrames % 15 == 0) {
            if (!(leftTime.getMinutes() == 0 && leftTime.getSeconds() == 0)) {
                leftTime.decrease();
            }
        }
        String display = leftTime.getMinutes() + (leftTime.getSeconds() < 10 ? ":0" : ":") + leftTime.getSeconds();
        textTime.setText(display);
    }

    public void updateElixirs(int userCount, int botCount) {
        userElixir.setText(Integer.toString(userCount));
        botElixir.setText(Integer.toString(botCount));
    }

    public void updateCrown(String botCrownText, String playerCrownText) {
        botCrown.setText(botCrownText);
        playerCrown.setText(playerCrownText);
    }

    /**
     * Repositions live-entity images and clears images for dead entities /
     * towers.
     */
    public void updateLivingAssets(ArrayList<TroopsCard> troops,
                                   ArrayList<Tower>      towers,
                                   ArrayList<Spells>     spells,
                                   ArrayList<Building>   buildings) {
        syncBattleCards(troops, spells, buildings);
        syncTowerImages(towers);
    }

    // ── Asset sync helpers ────────────────────────────────────────────────────

    private void syncBattleCards(ArrayList<TroopsCard> troops,
                                 ArrayList<Spells>     spells,
                                 ArrayList<Building>   buildings) {
        for (ImageView asset : battleCards) {
            Object data = asset.getUserData();
            if (data instanceof TroopsCard tc) {
                syncTroop(asset, tc, troops);
            } else if (data instanceof Spells s) {
                if (spells.stream().anyMatch(sp -> sp.equals(s) && !sp.isAlive())) asset.setImage(null);
            } else if (data instanceof Building b) {
                if (buildings.stream().anyMatch(bld -> bld.equals(b) && !bld.isAlive())) asset.setImage(null);
            }
        }
    }

    private void syncTroop(ImageView asset, TroopsCard old, ArrayList<TroopsCard> troops) {
        for (TroopsCard current : troops) {
            if (!current.getUuid().equals(old.getUuid())) continue;
            if (!current.isAlive()) {
                asset.setImage(null);
                asset.setUserData(null);
            } else {
                asset.setX(current.getCenterPositionX() - (float) TROOPS_SIZE / 2);
                asset.setY(current.getCenterPositionY() - (float) TROOPS_SIZE / 2);
                asset.setUserData(current);
            }
            break;
        }
    }

    private void syncTowerImages(ArrayList<Tower> towers) {
        for (Tower tower : towers) {
            if (tower.isAlive()) continue;
            ImageView iv = switch (tower.getTitle()) {
                case "userKingTower"       -> userKing;
                case "userRightQueenTower" -> userRightQueen;
                case "userLeftQueenTower"  -> userLeftQueen;
                case "botKingTower"        -> botKing;
                case "botLeftQueenTower"   -> botLeftQueen;
                case "botRightQueenTower"  -> botRightQueen;
                default                   -> null;
            };
            if (iv != null) iv.setImage(null);
        }
    }

    // ── Getters / setters ─────────────────────────────────────────────────────

    public LeftTime getLeftTime()             { return leftTime; }
    public int      getChosenCardIndex()      { return chosenCardIndex; }
    public void     setChosenCardIndex(int i) { this.chosenCardIndex = i; }

    public void setTextTime(Label l)   { this.textTime = l; timerFrames = 0; }
    public void setUserElixir(Label l) { this.userElixir = l; }
    public void setBotElixir(Label l)  { this.botElixir  = l; }
    public void setBotCrown(Label l)   { this.botCrown   = l; }
    public void setPlayerCrown(Label l){ this.playerCrown = l; }

    public void setArenaTowers(ArrayList<Tower> towers) {
        this.arenaTowers = towers;
        for (Tower t : towers) {
            ImageView iv = switch (t.getTitle()) {
                case "userKingTower"       -> userKing;
                case "userRightQueenTower" -> userRightQueen;
                case "userLeftQueenTower"  -> userLeftQueen;
                case "botKingTower"        -> botKing;
                case "botLeftQueenTower"   -> botLeftQueen;
                case "botRightQueenTower"  -> botRightQueen;
                default                   -> null;
            };
            if (iv != null) iv.setUserData(t);
        }
    }
}
