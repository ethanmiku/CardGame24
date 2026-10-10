module org.example.cardgame24 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.cardgame24 to javafx.fxml;
    exports org.example.cardgame24;
}