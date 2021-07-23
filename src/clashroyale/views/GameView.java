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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


/**
 * The type Game view.
 */
public class GameView extends Group {

    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //------------------------------------------Global Variables-----------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------

    /**
     * The Troops size.
     */
    final int TROOPS_SIZE = 60;
    /**
     * The User model.
     */
    UserModel userModel;
    /**
     * The Cards que.
     */
    Queue<Card> cardsQue;
    private Label textTime;
    private Label botCrown;
    private Label playerCrown;
    private Label userElixir;
    private Label botElixir;
    private AnchorPane anchorPane;
    private ImageView displayedCard1;
    private ImageView displayedCard2;
    private ImageView displayedCard3;
    private ImageView displayedCard4;

    private ImageView botKing;
    private ImageView botLeftQueen;
    private ImageView botRightQueen;
    private ImageView userKing;
    private ImageView userLeftQueen;
    private ImageView userRightQueen;

    private ImageView nextCard;
    private int flag;
    private int chosenCardIndex;
    private LeftTime leftTime;
    private ArrayList<ImageView> battleCards;
    private ArrayList<Tower> arenaTowers;

    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //------------------------------------Constructor And Initializations--------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------

    /**
     * Instantiates a new Game view.
     *
     * @param userModel the user model
     */
    public GameView(UserModel userModel) {
        this.userModel = userModel;
        leftTime = new LeftTime();
        battleCards = new ArrayList<>();
    }

    /**
     * Instantiate cards que image views.
     *
     * @param displayedCard1 the displayed card 1
     * @param displayedCard2 the displayed card 2
     * @param displayedCard3 the displayed card 3
     * @param displayedCard4 the displayed card 4
     * @param nextCard       the next card
     */
    public void instantiateCardsQueImageViews(ImageView displayedCard1, ImageView displayedCard2, ImageView displayedCard3,
                                              ImageView displayedCard4, ImageView nextCard) {
        this.displayedCard1 = displayedCard1;
        this.displayedCard2 = displayedCard2;
        this.displayedCard3 = displayedCard3;
        this.displayedCard4 = displayedCard4;
        this.nextCard = nextCard;

    }

    /**
     * Instantiate towers.
     *
     * @param botKing        the bot king
     * @param botLeftQueen   the bot left queen
     * @param botRightQueen  the bot right queen
     * @param userKing       the user king
     * @param userLeftQueen  the user left queen
     * @param userRightQueen the user right queen
     */
    public void instantiateTowers(ImageView botKing, ImageView botLeftQueen, ImageView botRightQueen,
                                  ImageView userKing, ImageView userLeftQueen, ImageView userRightQueen) {
        arenaTowers = new ArrayList<>();
        this.botKing = botKing;
        this.botLeftQueen = botLeftQueen;
        this.botRightQueen = botRightQueen;
        this.userKing = userKing;
        this.userLeftQueen = userLeftQueen;
        this.userRightQueen = userRightQueen;
    }

    /**
     * Sets anchor pane.
     *
     * @param anchorPane the anchor pane
     */
    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    /**
     * Prepare arena.
     */
    public void prepareArena() {
        cardsQue = new LinkedList<>(userModel.getChosenCardsList());
        Image[] initialCardsImages = new Image[5];
        for (int i = 0; i < 4; i++) {
            Card card = cardsQue.poll();
            if (card != null) {
                Image cardImage = getCardImageByTitle(card.getTitle());
                initialCardsImages[i] = cardImage;
                switch (i) {
                    case 0 -> displayedCard1.setUserData(card);
                    case 1 -> displayedCard2.setUserData(card);
                    case 2 -> displayedCard3.setUserData(card);
                    case 3 -> displayedCard4.setUserData(card);
                }
            }
        }
        Card card = cardsQue.poll();
        if (card != null) {
            Image cardImage = getCardImageByTitle(card.getTitle());
            initialCardsImages[4] = cardImage;
            nextCard.setUserData(card);
        }
        setInitialImages(initialCardsImages);
    }

    /**
     * initializes starting cards images for player
     *
     * @param initialCardsImages images to set
     */
    private void setInitialImages(Image[] initialCardsImages) {
        displayedCard1.setImage(initialCardsImages[0]);
        displayedCard2.setImage(initialCardsImages[1]);
        displayedCard3.setImage(initialCardsImages[2]);
        displayedCard4.setImage(initialCardsImages[3]);
        nextCard.setImage(initialCardsImages[4]);
    }


    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //-----------------------------------------View Main Methods-----------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------

    /**
     * Deploy troops.
     *
     * @param x                  the x
     * @param y                  the y
     * @param chosenToDeployCard the chosen to deploy card
     */
    public void deployTroops(float x, float y, Card chosenToDeployCard) {
        String user = chosenToDeployCard.getRelatedUser();
        if (!user.equals("simpleBot") && !user.equals("smartBot"))
            deployUserClick(x, y, chosenToDeployCard);
//            System.out.println(user+ "Not Equal Condition");
        else {
            deployBotClick(x, y, chosenToDeployCard);
        }
    }

    private void deployBotClick(float x, float y, Card chosenToDeployCard) {
        ImageView imageView;
        String title = chosenToDeployCard.getTitle();
        Image image;
        switch (title) {
            case "cannon" -> image = new Image(getClass().getResourceAsStream("../resources/chr/cannon/cannon1.png"));
            case "infernoTower" -> image = new Image(getClass().getResourceAsStream("../resources/chr/inferno" +
                    "/building_inferno_tower_sprite_2.png"));
            case "arrows" -> image = new Image(getClass().getResourceAsStream("../resources/Ski_trail_rating_symbol_red_circle.png"));
            case "fireball" -> image = new Image(getClass().getResourceAsStream("../resources/png-transparent-computer-icons-circle-circle-orange-sphere-desktop-wallpaper-thumbnail.png"));
            case "rage" -> image = new Image(getClass().getResourceAsStream("../resources/Pan_Blue_Circle.png"));
            case "archer" -> image = new Image(getClass().getResourceAsStream("../resources/chr/archer/chr_archer_sprite_000.png"));
            case "babyDragon" -> image = new Image(getClass().getResourceAsStream("../resources/chr/babydragon/chr_baby_dragon_sprite_003.png"));
            case "barbarian" -> image = new Image(getClass().getResourceAsStream("../resources/chr/barbarian/chr_barbarian_sprite_0252.png"));
            case "giant" -> image = new Image(getClass().getResourceAsStream("../resources/chr/giant/chr_giant_sprite_000.png"));
            case "miniPEKKA" -> image = new Image(getClass().getResourceAsStream("../resources/chr/minipekka/chr_mini_pekka_sprite_002.png"));
            case "valkyrie" -> image = new Image(getClass().getResourceAsStream("../resources/chr/valkyrie/chr_valkyrie_sprite_004.png"));
            case "wizard" -> image = new Image(getClass().getResourceAsStream("../resources/chr/wizard/chr_wizard_sprite_004.png"));
            default -> image = new Image(getClass().getResourceAsStream("../resources/ui/0.png"));
        }
        imageView = new ImageView(image);
        switch (title) {
            case "rage" -> {
                imageView.setOpacity(0.3);
                imageView.setX(x - (float) 50 / 2);
                imageView.setY(y - (float) 50 / 2);
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                imageView.setUserData(chosenToDeployCard);
            }
            case "fireball" -> {
                imageView.setOpacity(0.3);
                imageView.setX(x - (float) 25 / 2);
                imageView.setY(y - (float) 25 / 2);
                imageView.setFitWidth(25);
                imageView.setFitHeight(25);
                imageView.setUserData(chosenToDeployCard);
            }
            case "arrows" -> {
                imageView.setOpacity(0.3);
                imageView.setX(x - (float) 40 / 2);
                imageView.setY(y - (float) 40 / 2);
                imageView.setFitWidth(40);
                imageView.setFitHeight(40);
                imageView.setUserData(chosenToDeployCard);
            }
            case "cannon", "infernoTower" ->{ imageView.setX(x - (float) TROOPS_SIZE / 4);
                imageView.setY(y - (float) TROOPS_SIZE / 4);
                imageView.setFitWidth(TROOPS_SIZE/2);
                imageView.setFitHeight(TROOPS_SIZE/2);}


            default -> {
                imageView.setX(x - (float) TROOPS_SIZE / 2);
                imageView.setY(y - (float) TROOPS_SIZE / 2);
                imageView.setFitWidth(TROOPS_SIZE);
                imageView.setFitHeight(TROOPS_SIZE);
            }
        }
        imageView.setUserData(chosenToDeployCard);
        anchorPane.getChildren().add(imageView);
        battleCards.add(imageView);
    }

    private void deployUserClick(float x, float y, Card chosenToDeployCard) {
        ImageView imageView;
        Image image;
        String title = chosenToDeployCard.getTitle();
        switch (title) {
            case "cannon" -> image = new Image(getClass().getResourceAsStream("../resources/chr/cannon/cannon.png"));
            case "infernoTower" -> image = new Image(getClass().getResourceAsStream("../resources/chr/inferno" +
                    "/building_inferno_tower_sprite_2.png"));
            case "arrows" -> image = new Image(getClass().getResourceAsStream("../resources/Ski_trail_rating_symbol_red_circle.png"));
            case "fireball" -> image = new Image(getClass().getResourceAsStream("../resources/png-transparent-computer-icons-circle-circle-orange-sphere-desktop-wallpaper-thumbnail.png"));
            case "rage" -> image = new Image(getClass().getResourceAsStream("../resources/Pan_Blue_Circle.png"));
            case "archer" -> image = new Image(getClass().getResourceAsStream("../resources/chr/archer/chr_archer_sprite_069.png"));
            case "babyDragon" -> image = new Image(getClass().getResourceAsStream("../resources/chr/babydragon/chr_baby_dragon_sprite_111.png"));
            case "barbarian" -> image = new Image(getClass().getResourceAsStream("../resources/chr/barbarian/chr_barbarian_sprite_0251.png"));
            case "giant" -> image = new Image(getClass().getResourceAsStream("../resources/chr/giant/chr_giant_sprite_134.png"));
            case "miniPEKKA" -> image = new Image(getClass().getResourceAsStream("../resources/chr/minipekka/chr_mini_pekka_sprite_104.png"));
            case "valkyrie" -> image = new Image(getClass().getResourceAsStream("../resources/chr/valkyrie/chr_valkyrie_sprite_060.png"));
            case "wizard" -> image = new Image(getClass().getResourceAsStream("../resources/chr/wizard/chr_wizard_sprite_070.png"));
            default -> image = new Image(getClass().getResourceAsStream("../resources/ui/0.png"));
        }
        imageView = new ImageView(image);
        switch (title) {
            case "rage" -> {
                imageView.setOpacity(0.3);
                imageView.setX(x - (float) 50 / 2);
                imageView.setY(y - (float) 50 / 2);
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                imageView.setUserData(chosenToDeployCard);
            }
            case "fireball" -> {
                imageView.setOpacity(0.3);
                imageView.setX(x - (float) 25 / 2);
                imageView.setY(y - (float) 25 / 2);
                imageView.setFitWidth(25);
                imageView.setFitHeight(25);
                imageView.setUserData(chosenToDeployCard);
            }
            case "arrows" -> {
                imageView.setOpacity(0.3);
                imageView.setX(x - (float) 40 / 2);
                imageView.setY(y - (float) 40 / 2);
                imageView.setFitWidth(40);
                imageView.setFitHeight(40);
                imageView.setUserData(chosenToDeployCard);
            }
            case "cannon", "infernoTower" ->{ imageView.setX(x - (float) TROOPS_SIZE / 4);
                imageView.setY(y - (float) TROOPS_SIZE / 4);
                imageView.setFitWidth(TROOPS_SIZE/2);
                imageView.setFitHeight(TROOPS_SIZE/2);}
            default -> {
                imageView.setX(x - (float) TROOPS_SIZE / 2);
                imageView.setY(y - (float) TROOPS_SIZE / 2);
                imageView.setFitWidth(TROOPS_SIZE);
                imageView.setFitHeight(TROOPS_SIZE);
            }
        }
        imageView.setUserData(chosenToDeployCard);
        anchorPane.getChildren().add(imageView);
        battleCards.add(imageView);
        replaceDeployedCardWithNext();
    }

    /**
     * switches cards after deployment with next card.
     */
    private void replaceDeployedCardWithNext() {

        String title1 = ((Card) displayedCard1.getUserData()).getTitle();
        String title2 = ((Card) displayedCard2.getUserData()).getTitle();
        String title3 = ((Card) displayedCard3.getUserData()).getTitle();
        String title4 = ((Card) displayedCard4.getUserData()).getTitle();
        String nextTitle = ((Card) nextCard.getUserData()).getTitle();

        if (userModel.getChosenToDeployCard().getTitle().equals(title1)) {
            displayedCard1.setImage(getCardImageByTitle(nextTitle));
            displayedCard1.setUserData(nextCard.getUserData());
        } else if (userModel.getChosenToDeployCard().getTitle().equals(title2)) {
            displayedCard2.setImage(getCardImageByTitle(nextTitle));
            displayedCard2.setUserData(nextCard.getUserData());
        } else if (userModel.getChosenToDeployCard().getTitle().equals(title3)) {
            displayedCard3.setImage(getCardImageByTitle(nextTitle));
            displayedCard3.setUserData(nextCard.getUserData());
        } else if (userModel.getChosenToDeployCard().getTitle().equals(title4)) {
            displayedCard4.setImage(getCardImageByTitle(nextTitle));
            displayedCard4.setUserData(nextCard.getUserData());
        } else {
            System.out.println("Unable To Recon Chosen Card");
        }

        Card newNextCard = cardsQue.poll();
        if (newNextCard != null) {
            nextCard.setUserData(newNextCard);
            nextCard.setImage(getCardImageByTitle(newNextCard.getTitle()));
            cardsQue.add(userModel.getChosenToDeployCard());
            userModel.setChosenToDeployCard(null);
        } else {
            System.out.println("Unable To Recon Next Card");
        }

    }


    /**
     * Update timer.
     */
    public void updateTimer() {
        flag++;
        if (flag % 15 == 0) {
            int minutes = leftTime.getMinutes();
            int seconds = leftTime.getSeconds();
            if (!(minutes == 0 && seconds == 0)) {
                leftTime.decrease();
            }
        }

        String time1;
        if (leftTime.getSeconds() < 10) {
            time1 = leftTime.getMinutes() + ":0" + leftTime.getSeconds();
        } else {
            time1 = leftTime.getMinutes() + ":" + leftTime.getSeconds();
        }
        textTime.setText(time1);

    }

    /**
     * Update elixirs.
     *
     * @param userElixirCount the user elixir count
     * @param botElixirCount  the bot elixir count
     */
    public void updateElixirs(int userElixirCount, int botElixirCount) {
        userElixir.setText(Integer.toString(userElixirCount));
        botElixir.setText(Integer.toString(botElixirCount));
    }

    /**
     * Update living assets.
     *
     * @param existingTroops    the existing troops
     * @param existingTowers    the existing towers
     * @param existingSpells    the existing spells
     * @param existingBuildings the existing buildings
     */
    public void updateLivingAssets(ArrayList<TroopsCard> existingTroops, ArrayList<Tower> existingTowers,
                                   ArrayList<Spells> existingSpells, ArrayList<Building> existingBuildings) {
        for (ImageView asset : battleCards) {
            TroopsCard oldCard;
            Tower oldTower;

            if (asset.getUserData() instanceof TroopsCard) {
                oldCard = (TroopsCard) asset.getUserData();
                for (TroopsCard card : existingTroops) {
                    if (card.getUuid().equals(oldCard.getUuid())) {
                        if (!card.isAlive()) {
                            asset.setImage(null);
                            asset.setUserData(null);
                            System.out.println("Dead Troop Image Removed");
                        } else {
                            asset.setX(card.getCenterPositionX() - (float) TROOPS_SIZE / 2);
                            asset.setY(card.getCenterPositionY() - (float) TROOPS_SIZE / 2);
                            asset.setUserData(card);
                        }
                    }
                }
            } else if (asset.getUserData() instanceof Spells) {
                for (Spells spell : existingSpells) {
                    if (spell.equals(asset.getUserData()) && !spell.isAlive()) {
                        asset.setImage(null);
                    }
                }
            } else if (asset.getUserData() instanceof Building) {
                for (Building building : existingBuildings) {
                    if (building.isAlive())
                        System.out.println(building.getTitle() + " of " + building.getRelatedUser() + " hp : " + building.getHp());
                    if (building.equals(asset.getUserData()) && !building.isAlive())
                        asset.setImage(null);
                }
            }
        }

        for (Tower tower : existingTowers) {
            if (!tower.isAlive()) {
                switch (tower.getTitle()) {
                    case "userKingTower" -> userKing.setImage(null);
                    case "userRightQueenTower" -> userRightQueen.setImage(null);
                    case "userLeftQueenTower" -> userLeftQueen.setImage(null);
                    case "botKingTower" -> botKing.setImage(null);
                    case "botLeftQueenTower" -> botLeftQueen.setImage(null);
                    case "botRightQueenTower" -> botRightQueen.setImage(null);
                }
            }

        }
    }


    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //-----------------------------------------Helper Methods--------------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------

    private void reverseQueue() {
        Stack<Card> stack = new Stack<>();
        while (!cardsQue.isEmpty()) {
            stack.add(cardsQue.peek());
            cardsQue.remove();
        }
        while (!stack.isEmpty()) {
            cardsQue.add(stack.peek());
            stack.pop();
        }
    }

    private void removeCardFromQue(Card card) {
        System.out.println("New Queue");
        for (Card card2 : cardsQue) {
            System.out.println(card2.getTitle());
        }
        Queue<Card> temp = new LinkedList<>();
        for (Card card1 : cardsQue) {
            if (!card1.getTitle().equals(card.getTitle()))
                temp.add(card1);
        }
        cardsQue = temp;

        reverseQueue();
        cardsQue.add(card);

    }

    /**
     * @param title title of requested image
     * @return an image of card with elixir cost attached
     */
    private Image getCardImageByTitle(String title) {
        return switch (title) {
            case "cannon" -> new Image(getClass().getResourceAsStream("../resources/cardsWithLabel/cannon.png"));
            case "infernoTower" -> new Image(getClass().getResourceAsStream("../resources/cardsWithLabel/inferno-tower.png"));
            case "arrows" -> new Image(getClass().getResourceAsStream("../resources/cardsWithLabel/arrows.png"));
            case "fireball" -> new Image(getClass().getResourceAsStream("../resources/thumbCards/thumbfireball.png"));
            case "rage" -> new Image(getClass().getResourceAsStream("../resources/cardsWithLabel/rage.png"));
            case "archer" -> new Image(getClass().getResourceAsStream("../resources/cardsWithLabel/archers.png"));
            case "babyDragon" -> new Image(getClass().getResourceAsStream("../resources/cardsWithLabel/baby-dragon.png"));
            case "barbarian" -> new Image(getClass().getResourceAsStream("../resources/cardsWithLabel/barbarians.png"));
            case "giant" -> new Image(getClass().getResourceAsStream("../resources/cardsWithLabel/giant.png"));
            case "miniPEKKA" -> new Image(getClass().getResourceAsStream("../resources/cardsWithLabel/mini-pekka.png"));
            case "valkyrie" -> new Image(getClass().getResourceAsStream("../resources/cardsWithLabel/valkyrie.png"));
            case "wizard" -> new Image(getClass().getResourceAsStream("../resources/cardsWithLabel/wizard.png"));
            default -> null;
        };
    }


    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------Getters And Setters-----------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------

    /**
     * Gets chosen card index.
     *
     * @return the chosen card index
     */
    public int getChosenCardIndex() {
        return chosenCardIndex;
    }

    /**
     * Sets chosen card index.
     *
     * @param chosenCardIndex the chosen card index
     */
    public void setChosenCardIndex(int chosenCardIndex) {
        this.chosenCardIndex = chosenCardIndex;
    }

    /**
     * Sets text time.
     *
     * @param textTime the text time
     */
    public void setTextTime(Label textTime) {
        this.textTime = textTime;
        flag = 0;
    }

    /**
     * Sets user elixir.
     *
     * @param userElixir the user elixir
     */
    public void setUserElixir(Label userElixir) {
        this.userElixir = userElixir;
    }

    /**
     * Sets bot elixir.
     *
     * @param botElixir the bot elixir
     */
    public void setBotElixir(Label botElixir) {
        this.botElixir = botElixir;
    }

    /**
     * Sets bot crown.
     *
     * @param botCrown the bot crown
     */
    public void setBotCrown(Label botCrown) {
        this.botCrown = botCrown;
    }

    /**
     * Sets player crown.
     *
     * @param playerCrown the player crown
     */
    public void setPlayerCrown(Label playerCrown) {
        this.playerCrown = playerCrown;
    }

    /**
     * Sets arena towers.
     *
     * @param arenaTowers the arena towers
     */
    public void setArenaTowers(ArrayList<Tower> arenaTowers) {
        this.arenaTowers = arenaTowers;
        for (Tower tower : arenaTowers) {
            switch (tower.getTitle()) {
                case "userKingTower" -> userKing.setUserData(tower);
                case "userRightQueenTower" -> userRightQueen.setUserData(tower);
                case "userLeftQueenTower" -> userLeftQueen.setUserData(tower);
                case "botKingTower" -> botKing.setUserData(tower);
                case "botLeftQueenTower" -> botLeftQueen.setUserData(tower);
                case "botRightQueenTower" -> botRightQueen.setUserData(tower);
            }
        }
    }

    /**
     * Update crown.
     *
     * @param botCrown2    the bot crown 2
     * @param playerCrown2 the player crown 2
     */
    public void updateCrown(String botCrown2, String playerCrown2) {
        botCrown.setText(botCrown2);
        playerCrown.setText(playerCrown2);
    }

    /**
     * Gets left time.
     *
     * @return the left time
     */
    public LeftTime getLeftTime() {
        return leftTime;
    }
}
