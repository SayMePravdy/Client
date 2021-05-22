package client.gui.controllers;

import client.Client;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.ResourceBundle;

import static data.resources.LanguageBundles.*;

public class StartWindowController {

    ObservableList<String> languages = FXCollections.observableArrayList("Русский", "Український", "English", "Español");
    private static Stage startStage;

    public static void setBundle(ResourceBundle bundle) {
        StartWindowController.bundle = bundle;
    }

    //private int langNum;
    private static ResourceBundle bundle;
    public static void setStage(Stage stage) {
        StartWindowController.startStage = stage;
    }


    @FXML
    private Button authorizationButton;

    @FXML
    private Label ticketLabel;

    @FXML
    private Button exitButton;

    @FXML
    private Button registerButton;

    @FXML
    private ChoiceBox<String> language;

    @FXML
    void authorization(ActionEvent event) {
        LogInWindowController.setPrevWindow("/client/gui/scenes/start.fxml");
        LogInWindowController.setBundle(bundle);
        LogInWindowController.setStartStage(Client.changeWindow("/client/gui/scenes/logging.fxml", startStage, 400, 250));
    }


    @FXML
    void register(ActionEvent event) {
        RegisterWindowController.setPrevWindow("/client/gui/scenes/start.fxml");
        RegisterWindowController.setBundle(bundle);
        RegisterWindowController.setStartStage(Client.changeWindow("/client/gui/scenes/registration.fxml", startStage, 400, 250));
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(2);
    }

    @FXML
    void initialize() {
        setLanguage();
        language.setItems(languages);
        //language.setValue(bundle.getString("language"));
        language.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //System.out.println(newValue);
                switch ((int)newValue) {
                    case 0:
                        bundle = bundleRu;
                        break;
                    case 1:
                        bundle = bundleUa;
                        break;
                    case 2:
                        bundle = bundleEn;
                        break;
                    case 3:
                        bundle = bundleEs;
                        break;
                }
                setLanguage();
            }
        });
    }

    private void setLanguage() {
        ticketLabel.setText(bundle.getString("tickets"));
        authorizationButton.setText(bundle.getString("logInButton"));
        registerButton.setText(bundle.getString("registration"));
        exitButton.setText(bundle.getString("exit"));
        language.setValue(bundle.getString("language"));
    }

}

