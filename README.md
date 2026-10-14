# Card Game 24

A JavaFX application based on the Card Game 24. The goal is to use four randomly generated playing cards and arithmetic operations to create an expression that equals 24.

## Features

- Randomly generates four playing cards from a standard 52-card deck.
- Supports addition, subtraction, multiplication, and division.
- Allows parentheses to control the order of operations.
- Verifies that all four card values are used exactly once.
- Checks whether the entered expression equals 24.
- Includes a Find a Solution button that automatically searches for a valid expression.
- Includes a Refresh button to generate a new set of cards.
- Displays feedback through JavaFX alerts.

## How to Play

1. Run the application to generate four random cards.
2. Use each card's numerical value exactly once to create an expression that equals 24.
3. Enter your expression in the text field.
4. Click Verify to check your answer.
5. Click Find a Solution if you need help, or Refresh to generate new cards.

Card Values:
- Ace = 1
- Number cards = Face value
- Jack = 11
- Queen = 12
- King = 13

## Technologies Used

- Java
- JavaFX
- FXML and Scene Builder
- CSS
- Maven
- IntelliJ IDEA

## Project Structure

- HelloApplication.java — Launches the JavaFX application.
- HelloController.java — Handles card generation, button actions, and expression verification.
- ExpressionEvaluator.java — Evaluates mathematical expressions using stacks.
- SolutionFinder.java — Uses recursive backtracking to find expressions that equal 24.
- hello-view.fxml — Defines the user interface.
- style.css — Styles the application.
- Cards/ — Contains the playing card images.

## Running the Application

1. Clone or download the repository.
2. Open the project in IntelliJ IDEA.
3. Allow Maven to load the project dependencies.
4. Run HelloApplication.java to launch the game.
