package org.example.cardgame24;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;


import java.util.ArrayList;
import java.util.Collections;

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
    private void handleRefresh(){
        generateCards();
        expressionField.clear();
    }

    @FXML
    private TextField expressionField;

    @FXML
    private void handleVerify() {
        String expression = expressionField.getText();

        //check if all four card values are used
        if (!validateNumbers(expression)) {
            showAlert("Invalid Expression",
                    "You must use all four card values exactly once.");
            return;
        }

        try {
            //calculate the expression
            ExpressionEvaluator evaluator = new ExpressionEvaluator();
            double result = evaluator.evaluate(expression);

            //check if the result equals 24
            if (Math.abs(result - 24) < 1e-9) {
                showAlert("Correct!", "Your expression equals 24!");
            } else {
                showAlert("Incorrect",
                        "Your expression equals " + result + ", not 24.");
            }

        } catch (IllegalArgumentException e) {
            showAlert("Invalid Expression", e.getMessage());
        }
    }

    //display a message to the player
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleFindSolution(){

        //create a solution finder
        SolutionFinder finder = new SolutionFinder();

        //find a solution using the current four cards
        String solution = finder.findSolution(cardValues);

        if(solution != null){

            //display the solution in the text field
            expressionField.setText(solution);

            showAlert("Solution Found", "A possible solution is:" + solution);
        } else{
            //tell the player if no solution is found
            showAlert("No Solution", " No solution exists for these cards. Try Refresh.");
        }
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

    private boolean validateNumbers(String expression){
        ArrayList<Integer> numbers = new ArrayList<>();

        String[] tokens = expression.split("[^0-9]+");

        for(String token : tokens){
            if(!token.isEmpty()){
                numbers.add(Integer.parseInt(token));
            }
        }
        ArrayList<Integer> expected = new ArrayList<>(cardValues);

        Collections.sort(numbers);
        Collections.sort(expected);

        return numbers.equals(expected);
    }
}
