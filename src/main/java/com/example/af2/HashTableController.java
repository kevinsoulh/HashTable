package com.example.af2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.text.MessageFormat;

public class HashTableController {
    @FXML
    public VBox container;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public AnchorPane display;
    @FXML
    public TextField nameTextField;
    @FXML
    public ToggleGroup priorityToggleGroup;
    @FXML
    public RadioButton priority0RadioButton;
    @FXML
    public RadioButton priority1RadioButton;
    @FXML
    public TextArea removedNodesLog;

    public HashTable hashTable = new HashTable();

    @FXML
    public void initialize() {
        AnchorPane.setLeftAnchor(display, 0.0);
        AnchorPane.setRightAnchor(display, 0.0);

//        container.widthProperty().addListener((obs, oldVal, newVal) -> {
//            showTableOnDisplay(hashTable, newVal.doubleValue());
//        });
    }

    @FXML
    protected void onAddItemButtonClick() {
        String name = nameTextField.getText();
        name = name.trim();
        if(name.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You didn't enter a name.", ButtonType.OK);
            alert.show();
            return;
        }

        int priority = 0;
        if (priority1RadioButton.isSelected()) {
            priority = 1;
        }

        hashTable.insert(name, priority);
    }

    @FXML
    public void onRemoveItemButtonClick() {
        String name = nameTextField.getText();
        name = name.trim();

        if(name.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You didn't enter a name.", ButtonType.OK);
            alert.show();
            return;
        }

        try {
            Node removedNode = hashTable.remove(name);

            int key = HashTable.key(name);
            int hash = HashTable.hash(key, hashTable.linkedLists.length);

            removedNodesLog.appendText("====== Removed node ======" + "\n");
            removedNodesLog.appendText("Name: " + name + "\n");
            removedNodesLog.appendText("Key: " + key + "\n");
            removedNodesLog.appendText("Priority: " + removedNode.priority + "\n");
            removedNodesLog.appendText("Hash: " + hash + "\n\n");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry, the name does not exist.", ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    public void onListButtonClick() {
        this.showTableOnDisplay();
    }

    private void showTableOnDisplay() {
        double displayWidth = scrollPane.getBoundsInParent().getWidth();

        display.getChildren().clear();

        // Step 1: render the array
        renderHashTableArray(displayWidth);

        // Step 2: render the nodes
        renderNodes(displayWidth);

        // Step 3: draw lines from the array to the first Node
        renderLineFromArrayToFirstNodes(displayWidth);
    }



    /**
     * Renders the array on top of the diagram
     * @param displayWidth width of the available space
     */
    private void renderHashTableArray(double displayWidth) {
        int linkedListCount = hashTable.linkedLists.length;
        double width = 50;
        double totalWidth = width * linkedListCount;
        for (int i = 0; i < linkedListCount; i++) {
            double displayCenterX = displayWidth / 2;
            double x = (i * width) + displayCenterX - (totalWidth / 2);

            Rectangle rect = new Rectangle(x, 0, width, width);
            rect.setStroke(Color.BLACK);
            rect.setFill(Color.WHITE);
            rect.setStrokeWidth(1);
            display.getChildren().add(rect);

            Text indexLabel = new Text(x, (width / 2) + 5, Integer.toString(i));
            indexLabel.setFill(Color.BLACK);
            indexLabel.setTextAlignment(TextAlignment.CENTER);
            indexLabel.setWrappingWidth(width);
            display.getChildren().add(indexLabel);

            if(display.getHeight() < width) {
                display.setPrefHeight(width);
            }
        }
    }

    /**
     * Renders the nodes
     * @param displayWidth width of the available space
     */
    private void renderNodes(double displayWidth) {
        int linkedListCount = hashTable.linkedLists.length;

        double offsetY = 100;

        double width = 100;
        double height = 50;
        double spacingY = 20;

        double totalWidth = width * linkedListCount;
        for (int i = 0; i < linkedListCount; i++) {
            PriorityLinkedList currentList = hashTable.linkedLists[i];
            if (currentList == null) continue;

            double displayCenterX = displayWidth / 2;
            double x = (i * width) + displayCenterX - (totalWidth / 2);

            int nodeCount = currentList.getCount();
            Node currentNode = currentList.getHead();
            double currentY = offsetY;
            for (int j = 0; j < nodeCount; j++) {


                String name = currentNode.name;
                int key = HashTable.key(name);
                int priority = currentNode.priority;
                int hash = HashTable.hash(key, hashTable.linkedLists.length);

                String labelString = MessageFormat.format("Name: {0}\nKey: {1}\nPriority: {2}\nHash: {3}", name, key, priority, hash);
                Text nodeLabel = new Text(labelString);
                nodeLabel.setX(x);
                nodeLabel.setY(currentY + 12);
                nodeLabel.setFill(Color.BLACK);
                nodeLabel.setWrappingWidth(width);

                Rectangle rect = new Rectangle(x, currentY, nodeLabel.getBoundsInLocal().getWidth(), nodeLabel.getBoundsInLocal().getHeight());
                rect.setStroke(Color.BLACK);
                rect.setFill(Color.WHITE);
                rect.setStrokeWidth(1);
                display.getChildren().add(rect);
                display.getChildren().add(nodeLabel);

                if(display.getHeight() < currentY + height) {
                    display.setPrefHeight(currentY + height);
                }

                currentY += height + spacingY;
                currentNode = currentNode.nextNode;



            }


        }
    }


    /**
     * Renders a line from the array to the first nodes
     * @param displayWidth width of the available space
     */
    private void renderLineFromArrayToFirstNodes(double displayWidth) {
//        int linkedListCount = hashTable.linkedLists.length;
//
//        double offsetY = 100;
//
//        double width = 100;
//        double height = 50;
//        double spacingY = 20;
//
//        double totalWidth = width * linkedListCount;
//        for (int i = 0; i < linkedListCount; i++) {
//            PriorityLinkedList currentList = hashTable.linkedLists[i];
//            if (currentList == null) continue;
//
//            double displayCenterX = displayWidth / 2;
//            double x = (i * width) + displayCenterX - (totalWidth / 2);
//
//            int nodeCount = currentList.getCount();
//            Node currentNode = currentList.getHead();
//            double currentY = offsetY;
//        }
    }


}