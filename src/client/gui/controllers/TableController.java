package client.gui.controllers;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class TableController {

    private static Stage startStage;

    @FXML
    private Button backButton;

    @FXML
    private TableView<?> table;

    public static void setStage(Stage startStage) {
        TableController.startStage = startStage;
    }

    @FXML
    void sort(ActionEvent event) {

    }

    @FXML
    void back(ActionEvent event) {
        CommandsWindowController.setStartStage(Client.changeWindow("/client/gui/scenes/commands.fxml", startStage, 450, 530));
    }

}

