package clashroyale.views;

import clashroyale.models.UserModel;
import clashroyale.models.cardsmodels.troops.Card;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.LinkedList;
import java.util.Queue;

public class GameView extends Group {
    final int TROOPS_SIZE = 30;
    UserModel userModel;
    Queue<Card> cardsQue;
    private ImageView imageView;
    private Image image;
    private AnchorPane anchorPane;
    private ImageView displayedCard1;
    private ImageView displayedCard2;
    private ImageView displayedCard3;
    private ImageView displayedCard4;
    private ImageView nextCard;

    public GameView(UserModel userModel) {
        this.userModel = userModel;
    }

    public void instantiateCardsQueImageViews(ImageView displayedCard1, ImageView displayedCard2, ImageView displayedCard3,
                                              ImageView displayedCard4, ImageView nextCard) {
        this.displayedCard1 = displayedCard1;
        this.displayedCard2 = displayedCard2;
        this.displayedCard3 = displayedCard3;
        this.displayedCard4 = displayedCard4;
        this.nextCard = nextCard;

    }

    public void deployTroops(float x, float y, Card chosenToDeployCard) {
        String title = chosenToDeployCard.getTitle();
        if (title.equals("cannon")) {
            if (!userModel.getUsername().equals("simpleBot") || !userModel.getUsername().equals("smartBot")) {

            } else {

            }
        } else if (title.equals("infernoTower")) {
            if (!userModel.getUsername().equals("simpleBot") || !userModel.getUsername().equals("smartBot")) {

            } else {

            }

        } else if (title.equals("arrows")) {
            if (!userModel.getUsername().equals("simpleBot") || !userModel.getUsername().equals("smartBot")) {

            } else {

            }
        } else if (title.equals("fireball")) {
            if (!userModel.getUsername().equals("simpleBot") || !userModel.getUsername().equals("smartBot")) {

            } else {

            }
        } else if (title.equals("rage")) {
            if (!userModel.getUsername().equals("simpleBot") || !userModel.getUsername().equals("smartBot")) {

            } else {

            }
        } else if (title.equals("archer")) {
            if (!userModel.getUsername().equals("simpleBot") || !userModel.getUsername().equals("smartBot")) {
                image = new Image(getClass().getResourceAsStream("../resources/chr/archer/chr_archer_sprite_177.png"));
            } else {

            }
        } else if (title.equals("babyDragon")) {
            if (!userModel.getUsername().equals("simpleBot") || !userModel.getUsername().equals("smartBot")) {

            } else {

            }
        } else if (title.equals("barbarian")) {
            if (!userModel.getUsername().equals("simpleBot") || !userModel.getUsername().equals("smartBot")) {

            } else {

            }
        } else if (title.equals("giant")) {
            if (!userModel.getUsername().equals("simpleBot") || !userModel.getUsername().equals("smartBot")) {

            } else {

            }
        } else if (title.equals("miniPEKKA")) {
            if (!userModel.getUsername().equals("simpleBot") || !userModel.getUsername().equals("smartBot")) {

            } else {

            }
        } else if (title.equals("valkyrie")) {
            if (!userModel.getUsername().equals("simpleBot") || !userModel.getUsername().equals("smartBot")) {

            } else {

            }
        } else if (title.equals("wizard")) {
            if (!userModel.getUsername().equals("simpleBot") || !userModel.getUsername().equals("smartBot")) {

            } else {

            }
        }

        image = new Image(getClass().getResourceAsStream("../resources/chr/archer/chr_archer_sprite_177.png"));
        System.out.println(x + "  " + y);
        imageView = new ImageView(image);
        imageView.setX(x - (float) TROOPS_SIZE / 2);
        imageView.setY(y - (float) TROOPS_SIZE / 2);
        imageView.setFitWidth(TROOPS_SIZE);
        imageView.setFitHeight(TROOPS_SIZE);
        anchorPane.getChildren().add(imageView);
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public void prepareArena() {
        cardsQue = new LinkedList<>(userModel.getChosenCardsList());
        for (int i = 0; i < 4; i++) {
            Card card = cardsQue.poll();
            if (card != null) {
                String title = card.getTitle();
                System.out.println(title);
            }
        }
    }
}
