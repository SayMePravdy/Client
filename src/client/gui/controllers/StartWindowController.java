package client.gui.controllers;

import client.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;

public class StartWindowController {

    ObservableList<String> languages = FXCollections.observableArrayList("Русский", "Український", "português", "Español");
    private static Stage startStage;

    public static void setStage(Stage stage) {
        StartWindowController.startStage = stage;
    }

    @FXML
    private Button authorizationButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button registerButton;

    @FXML
    private ChoiceBox<String> language;

    @FXML
    void authorization(ActionEvent event) {
        LogInWindowController.setPrevWindow("/client/gui/scenes/start.fxml");
        LogInWindowController.setStartStage(Client.changeWindow("/client/gui/scenes/logging.fxml", startStage, 400, 250));
    }


    @FXML
    void register(ActionEvent event) {
        RegisterWindowController.setPrevWindow("/client/gui/scenes/start.fxml");
        RegisterWindowController.setStartStage(Client.changeWindow("/client/gui/scenes/registration.fxml", startStage, 400, 250));
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(2);
    }

    @FXML
    void initialize() {
        language.setValue("Русский");
        language.setItems(languages);

    }

}

