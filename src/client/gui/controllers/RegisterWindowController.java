package client.gui.controllers;

import client.Client;
import data.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterWindowController {

    public Label passwordLabel;
    public Button backButoon;
    public Label loginLabel;
    public Label registrationLabel;

    public static void setStartStage(Stage startStage) {
        RegisterWindowController.startStage = startStage;
    }

    public static void setBundle(ResourceBundle bundle) {
        RegisterWindowController.bundle = bundle;
    }

    //private int langNum;
    private static ResourceBundle bundle;

    public static void setPrevWindow(String prevWindow) {
        RegisterWindowController.prevWindow = prevWindow;
    }

    private static Stage startStage;

    private static String prevWindow;

    @FXML
    private PasswordField password;

    @FXML
    private Button backButton;

    @FXML
    private Button registerButton;

    @FXML
    private TextField login;

    @FXML
    void getLogin(ActionEvent event) {
        System.out.println(login.getText());
    }

    @FXML
    void getPassword(ActionEvent event) {
        System.out.println(password.getText());
    }

    @FXML
    void register(ActionEvent event) {
        Client.setLogin(login.getText());
        Client.setPassword(password.getText());
        List<Object> args = new ArrayList<>();
        args.add(Client.getLogin());
        args.add(Client.getPassword());
        Data data = new Data("register", args, Client.getLogin(), Client.getPassword());

        if (Client.getLogin().equals("")) {
            Client.showWindow(200, 400, bundle.getString("Login can't be empty"), Color.RED);
        } else {
            try {
                String ans = Client.sendCommand(data);
                if (ans.equals("User sign up")) {
                    CommandsWindowController.setBundle(bundle);
                    CommandsWindowController.setStartStage(Client.changeWindow("/client/gui/scenes/commands.fxml", startStage, 400, 530));
                }else {
                    Client.showWindow(200, 400, bundle.getString(ans), Color.RED);
                }
            }catch (IOException e) {
                Client.showWindow(200, 400, bundle.getString("Server is tired. Try to reconnect later"), Color.RED);
            }
        }
    }

    @FXML
    void back(ActionEvent event) {
        //StartWindowController.setBundle(bundle);
        StartWindowController.setStage(Client.changeWindow(prevWindow, startStage, 435, 100));
    }

    @FXML
    void initialize() {
        setLanguage();
    }

    private void setLanguage() {
        registrationLabel.setText(bundle.getString("registration"));
        loginLabel.setText(bundle.getString("login"));
        passwordLabel.setText(bundle.getString("password"));
        registerButton.setText(bundle.getString("register"));
        backButoon.setText(bundle.getString("back"));
    }

}

