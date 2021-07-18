package clashroyale.views;

import clashroyale.models.UserModel;
import clashroyale.models.cardsmodels.troops.Card;
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
    private ImageView imageView;
    private Image image;
    private AnchorPane anchorPane;
    private ImageView displayedCard1;
    private ImageView displayedCard2;
    private ImageView displayedCard3;
    private ImageView displayedCard4;
    private ImageView nextCard;
    private int flag;
    private int chosenCardIndex;
    private LeftTime leftTime;
    private ArrayList<ImageView> battleCards;

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
                cardsQue.add(card);
            }
        }
        Card card = cardsQue.poll();
        if (card != null) {
            Image cardImage = getCardImageByTitle(card.getTitle());
            initialCardsImages[4] = cardImage;
            nextCard.setUserData(card);
            cardsQue.add(card);
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
        String title = chosenToDeployCard.getTitle();
        if (title.equals("cannon")) {

        } else if (title.equals("infernoTower")) {
            image = new Image(getClass().getResourceAsStream("../resources/chr/inferno/building_inferno_tower_sprite_2.png"));
        } else if (title.equals("arrows")) {

        } else if (title.equals("fireball")) {

        } else if (title.equals("rage")) {

        } else if (title.equals("archer")) {
            if (!userModel.getUsername().equals("simpleBot") || !userModel.getUsername().equals("smartBot")) {
                image = new Image(getClass().getResourceAsStream("../resources/chr/archer/chr_archer_sprite_069.png"));
            } else {
                image = new Image(getClass().getResourceAsStream("../resources/chr/archer/chr_archer_sprite_000.png"));
            }

        } else if (title.equals("babyDragon")) {
            if (!userModel.getUsername().equals("simpleBot") || !userModel.getUsername().equals("smartBot")) {
                image = new Image(getClass().getResourceAsStream("../resources/chr/babydragon/chr_baby_dragon_sprite_111.png"));
            } else {
                image = new Image(getClass().getResourceAsStream("../resources/chr/babydragon/chr_baby_dragon_sprite_003.png"));
            }
        } else if (title.equals("barbarian")) {
            if (!userModel.getUsername().equals("simpleBot") || !userModel.getUsername().equals("smartBot")) {
                image = new Image(getClass().getResourceAsStream("../resources/chr/barbarian/chr_barbarian_sprite_0251.png"));
            } else {
                image = new Image(getClass().getResourceAsStream("../resources/chr/barbarian/chr_barbarian_sprite_0252.png"));
            }
        } else if (title.equals("giant")) {
            if (!userModel.getUsername().equals("simpleBot") || !userModel.getUsername().equals("smartBot")) {
                image = new Image(getClass().getResourceAsStream("../resources/chr/giant/chr_giant_sprite_134.png"));
            } else {
                image = new Image(getClass().getResourceAsStream("../resources/chr/giant/chr_giant_sprite_000.png"));
            }
        } else if (title.equals("miniPEKKA")) {
            if (!userModel.getUsername().equals("simpleBot") || !userModel.getUsername().equals("smartBot")) {
                image = new Image(getClass().getResourceAsStream("../resources/chr/minipekka/chr_mini_pekka_sprite_104.png"));
            } else {
                image = new Image(getClass().getResourceAsStream("../resources/chr/minipekka/chr_mini_pekka_sprite_002.png"));
            }
        } else if (title.equals("valkyrie")) {
            if (!userModel.getUsername().equals("simpleBot") || !userModel.getUsername().equals("smartBot")) {
                image = new Image(getClass().getResourceAsStream("../resources/chr/valkyrie/chr_valkyrie_sprite_060.png"));
            } else {
                image = new Image(getClass().getResourceAsStream("../resources/chr/valkyrie/chr_valkyrie_sprite_004.png"));
            }
        } else if (title.equals("wizard")) {
            if (!userModel.getUsername().equals("simpleBot") || !userModel.getUsername().equals("smartBot")) {
                image = new Image(getClass().getResourceAsStream("../resources/chr/wizard/chr_wizard_sprite_070.png"));
            } else {
                image = new Image(getClass().getResourceAsStream("../resources/chr/wizard/chr_wizard_sprite_004.png"));
            }
        }
        System.out.println(x + "  " + y);
        System.out.println(image.getWidth());
        imageView = new ImageView(image);
        imageView.setX(x - (float) TROOPS_SIZE / 2);
        imageView.setY(y - (float) TROOPS_SIZE / 2);
        imageView.setFitWidth(TROOPS_SIZE);
        imageView.setFitHeight(TROOPS_SIZE);
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
            userModel.setChosenToDeployCard(null);
        } else {
            System.out.println("Unable To Recon Next Card");
        }
    }


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

    public void updateLivingAssets(ArrayList<Card> existingCards, ArrayList<Tower> existingTowers) {
        for (ImageView asset : battleCards) {
            Card oldCard;
            Tower oldTower;
            Object userData = asset.getUserData();
            if (userData instanceof Card) {
                oldCard = (Card) userData;
                for (Card card : existingCards) {
                    if (card.getUuid().equals(oldCard.getUuid())) {
                        if (!card.isAlive()) {
                            asset.setImage(null);
                        } else {
                            asset.setX(card.getCenterPositionX() - card.getRadius());
                            asset.setY(card.getCenterPositionY() - card.getRadius());
                        }
                    }
                }
            } else {
                oldTower = (Tower) userData;
                for (Tower tower : existingTowers) {
                    if (tower.getUuid().equals(oldTower.getUuid()) && !tower.isAlive())
                        asset.setImage(null);
                }
            }

        }
    }

    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //-----------------------------------------Helper Methods--------------------------------------------------
    //---------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------


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

    public void setTextTime(Label textTime) {
        this.textTime = textTime;
        flag = 0;
    }

}
