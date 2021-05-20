package client.gui.controllers;

import client.Client;
import data.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterWindowController {

    public static void setStartStage(Stage startStage) {
        RegisterWindowController.startStage = startStage;
    }

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
            Client.showWindow(200, 400, "Login can't be empty", Color.RED);
        } else {
            try {
                String ans = Client.sendCommand(data);
                if (ans.equals("User sign up")) {
                    CommandsWindowController.setStartStage(Client.changeWindow("/client/gui/scenes/commands.fxml", startStage, 400, 530));
                }else {
                    Client.showWindow(200, 400, ans, Color.RED);
                }
            }catch (IOException e) {
                Client.showWindow(200, 400, "Server is tired. Try to reconnect later", Color.RED);
            }
        }


    }

    @FXML
    void back(ActionEvent event) {
        StartWindowController.setStage(Client.changeWindow(prevWindow, startStage, 435, 100));
    }

}

