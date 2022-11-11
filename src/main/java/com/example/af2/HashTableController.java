package com.example.af2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HashTableController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}