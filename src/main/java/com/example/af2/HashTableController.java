package com.example.af2;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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

    private final double hashTableArrayWidth = 50;
    private final double hashTableArrayHeight = 50;


    private final double nodeOffsetTop = 100;
    private final double nodeWidth = 100;
    private final double nodeSpacingHorizontal = 20;
    private final double nodeSpacingVertical = 20;


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
            Alert alert = new Alert(Alert.AlertType.ERROR, "Você precisa digitar o nome.", ButtonType.OK);
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
            Alert alert = new Alert(Alert.AlertType.ERROR, "Você precisa digitar o nome.", ButtonType.OK);
            alert.show();
            return;
        }

        try {
            Node removedNode = hashTable.remove(name);

            int key = HashTable.key(name);
            int hash = HashTable.hash(key, hashTable.linkedLists.length);

            removedNodesLog.appendText("====== Nó removido ======" + "\n");
            removedNodesLog.appendText("Nome: " + name + "\n");
            removedNodesLog.appendText("Chave: " + key + "\n");
            removedNodesLog.appendText("Prioridade: " + removedNode.priority + "\n");
            removedNodesLog.appendText("Hash: " + hash + "\n\n");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Desculpe, este nome não existe.", ButtonType.OK);
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


    }



    /**
     * Renders the array on top of the diagram
     * @param displayWidth width of the available space
     */
    private void renderHashTableArray(double displayWidth) {
        int linkedListCount = hashTable.linkedLists.length;
        double totalArrayWidth = hashTableArrayWidth * linkedListCount;
        double totalNodesWidth = (nodeWidth + nodeSpacingHorizontal) * linkedListCount;
        for (int i = 0; i < linkedListCount; i++) {
            double displayCenterX = displayWidth / 2;
            double x = (i * hashTableArrayWidth) + displayCenterX - (totalArrayWidth / 2);

            Rectangle rect = new Rectangle(x, 0, hashTableArrayWidth, hashTableArrayHeight);
            rect.setStroke(Color.BLACK);
            rect.setFill(Color.WHITE);
            rect.setStrokeWidth(1);
            display.getChildren().add(rect);

            Text indexLabel = new Text(x, (hashTableArrayHeight / 2) + 5, Integer.toString(i));
            indexLabel.setFill(Color.BLACK);
            indexLabel.setTextAlignment(TextAlignment.CENTER);
            indexLabel.setWrappingWidth(hashTableArrayWidth);
            display.getChildren().add(indexLabel);

            if(display.getHeight() < hashTableArrayHeight) {
                display.setPrefHeight(hashTableArrayHeight);
            }

            if(hashTable.linkedLists[i] != null) {
                double nodeX = (i * (nodeWidth + nodeSpacingHorizontal)) + displayCenterX - (totalNodesWidth / 2);
                renderNodes(hashTable.linkedLists[i],
                        rect.getX(), rect.getY() + rect.getHeight(),
                        nodeX, rect.getY() + rect.getHeight() + nodeOffsetTop
                );
            }
        }
    }

    /**
     * Renders the nodes
     */
    private void renderNodes(PriorityLinkedList linkedList, double arrayXPos, double arrayYPos, double nodeXPos, double nodeYPos) {
        int nodeCount = linkedList.getCount();
        Node currentNode = linkedList.getHead();
        if(currentNode == null) return;

        double currentY = nodeYPos;
        for (int j = 0; j < nodeCount; j++) {


            String name = currentNode.name;
            int key = HashTable.key(name);
            int priority = currentNode.priority;
            int hash = HashTable.hash(key, hashTable.linkedLists.length);

            String labelString = MessageFormat.format("Nome: {0}\nChave: {1}\nPrioridade: {2}\nHash: {3}", name, key, priority, hash);
            Text nodeLabel = new Text(labelString);
            nodeLabel.setX(nodeXPos);
            nodeLabel.setY(currentY + 12);
            nodeLabel.setFill(Color.BLACK);
            nodeLabel.setWrappingWidth(nodeWidth);

            Bounds nodeLabelBounds = nodeLabel.getBoundsInLocal();
            double nodeLabelWidth = nodeLabelBounds.getWidth();
            double nodeLabelHeight = nodeLabelBounds.getHeight();

            Rectangle rect = new Rectangle(nodeXPos, currentY, nodeLabelWidth, nodeLabelHeight);
            rect.setStroke(Color.BLACK);
            rect.setFill(Color.WHITE);
            rect.setStrokeWidth(1);
            display.getChildren().add(rect);
            display.getChildren().add(nodeLabel);

            if(display.getHeight() < currentY + nodeLabelHeight) {
                display.setPrefHeight(currentY + nodeLabelHeight);
            }

            // Step 3: draw lines from the array to the first Node
            if(currentNode == linkedList.getHead()) {
                // Renders a line from the array to the first nodes
                Line line = new Line(arrayXPos + (hashTableArrayWidth / 2), arrayYPos, nodeXPos + (nodeWidth / 2), nodeYPos);
                display.getChildren().add(line);
            }

            double nextY = currentY + nodeLabelHeight + nodeSpacingVertical;

            // Step 3: draw lines between the nodes
            if(currentNode.nextNode != null) {
                double line1X = nodeXPos + (nodeWidth / 2);

                Line line1 = new Line(line1X - 20, currentY + nodeLabelHeight, line1X - 20, nextY);
                Line line2 = new Line(line1X + 20, currentY + nodeLabelHeight, line1X + 20, nextY);
                display.getChildren().add(line1);
                display.getChildren().add(line2);
            }

            currentY = nextY;
            currentNode = currentNode.nextNode;


        }


    }


}