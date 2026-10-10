package org.example.cardgame24;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class HelloController {

    @FXML
    private ImageView card1Image;

    @FXML
    private ImageView card2Image;

    @FXML
    private ImageView card3Image;

    @FXML
    private ImageView card4Image;

    @FXML
    private void handleRefresh(){ generateCards();}

    @FXML
    private void handleVerify(){

    }

    @FXML
    private void handleFindSolution(){

    }

    private final ArrayList<String> deck = new ArrayList<>();
    private final ArrayList<Integer> cardValues = new ArrayList<>();

    @FXML
    public void initialize() {
        createDeck();
        generateCards();
    }

    private void createDeck() {
        String[] suits = {"clubs", "diamonds", "hearts", "spades"};

        for (String suit : suits) {
            for (int value = 1; value <= 13; value++) {
                String rank;

                switch (value) {
                    case 1 -> rank = "ace";
                    case 11 -> rank = "jack";
                    case 12 -> rank = "queen";
                    case 13 -> rank = "king";
                    default -> rank = String.valueOf(value);
                }
                deck.add(rank + "_of_" + suit + ".png");
            }
        }
    }

    private void generateCards(){
        Collections.shuffle(deck);
        cardValues.clear();

        ImageView[] imageViews = {card1Image,card2Image,card3Image,card4Image};

        for(int i = 0; i < 4; i++){
            String filename = deck.get(i);
            String rank = filename.split("_of_")[0];

            int value;

            switch(rank){
                case "ace" -> value = 1;
                case "jack" -> value = 11;
                case "queen" -> value = 12;
                case "king" -> value = 13;
                default -> value = Integer.parseInt(rank);
            }

            cardValues.add(value);

            String path = "/org/example/cardgame24/Cards/" + filename;

            Image image = new Image(getClass().getResource(path).toExternalForm());

            imageViews[i].setImage(image);
        }
    }
}