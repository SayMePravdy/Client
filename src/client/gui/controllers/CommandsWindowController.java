package client.gui.controllers;

import client.Client;
import data.Data;
import exceptions.RecursiveScript;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import processor.FileProcessor;

import java.io.IOException;
import java.util.*;

import static data.resources.LanguageBundles.*;
import static data.resources.LanguageBundles.bundleEs;

public class CommandsWindowController {

    public Button scriptButton;
    ObservableList<String> languages = FXCollections.observableArrayList("Русский", "Український", "English", "Español");
    private static Stage startStage;

    private Stage getStage;
    private String arg;

    public static void setStartStage(Stage startStage) {
        CommandsWindowController.startStage = startStage;
    }

    @FXML
    private Button removeGreaterButton;

    @FXML
    private Button helpButton;

    @FXML
    public Button tableButton;

    @FXML
    public Button visualizationButton;

    @FXML
    private Button maxByCommentButton;

    @FXML
    private Button addIfMaxButton;

    @FXML
    private Button addButton;

    @FXML
    private ChoiceBox<String> language;

    @FXML
    private Button sumOfDiscountButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button printUniquePriceButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button showButton;

    @FXML
    private Button infoButton;

    @FXML
    private Button userNameButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button removeByIdButton;

    @FXML
    private Button addIfMinButton;

    public static void setBundle(ResourceBundle bundle) {
        CommandsWindowController.bundle = bundle;
    }

    //private int langNum;
    private static ResourceBundle bundle;

    @FXML
    void initialize() {
        setLanguage();
        userNameButton.setText(Client.getLogin());
        language.setItems(languages);
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
        language.setValue(bundle.getString("language"));
        addButton.setText(bundle.getString("add"));
        addIfMaxButton.setText(bundle.getString("addIfMax"));
        addIfMinButton.setText(bundle.getString("addIfMin"));
        maxByCommentButton.setText(bundle.getString("maxByComment"));
        printUniquePriceButton.setText(bundle.getString("printUniquePrices"));
        tableButton.setText(bundle.getString("table"));
        removeByIdButton.setText(bundle.getString("removeById"));
        clearButton.setText(bundle.getString("clear"));
        infoButton.setText(bundle.getString("info"));
        sumOfDiscountButton.setText(bundle.getString("sumOfDiscount"));
        removeGreaterButton.setText(bundle.getString("removeGreater"));
        scriptButton.setText(bundle.getString("executeScript"));
        updateButton.setText(bundle.getString("update"));
        visualizationButton.setText(bundle.getString("visualize"));
        exitButton.setText(bundle.getString("exit"));
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(2);
    }

    @FXML
    void add(ActionEvent event) {
        GetTicketController.setTicket(null);
        GetTicketController.setBundle(bundle);
        GetTicketController.setCommandName("add");
        GetTicketController.setPrevWindow("/client/gui/scenes/commands.fxml");
        GetTicketController.setStage(Client.changeWindow("/client/gui/scenes/ticket.fxml", startStage, 400, 300));
    }

    @FXML
    void addIfMin(ActionEvent event) {
        GetTicketController.setTicket(null);
        GetTicketController.setBundle(bundle);
        GetTicketController.setCommandName("add_if_min");
        GetTicketController.setPrevWindow("/client/gui/scenes/commands.fxml");
        GetTicketController.setStage(Client.changeWindow("/client/gui/scenes/ticket.fxml", startStage, 400, 300));
    }

    @FXML
    void addIfMax(ActionEvent event) {
        GetTicketController.setBundle(bundle);
        GetTicketController.setTicket(null);
        GetTicketController.setCommandName("add_if_max");
        GetTicketController.setPrevWindow("/client/gui/scenes/commands.fxml");
        GetTicketController.setStage(Client.changeWindow("/client/gui/scenes/ticket.fxml", startStage, 400, 300));
    }

    private void send(Data data, double height, double width) {
        try {
            String ans;
            String servAns = Client.sendCommand(data);
            try {
                ans = bundle.getString(servAns);
            } catch (MissingResourceException e) {
                ans = servAns;
            }

            //CommandsWindowController.setStartStage(Client.changeWindow("/client/gui/scenes/commands.fxml", startStage, 450, 530));
            Client.showWindow(height, width, ans, Color.BLACK);
        } catch (IOException e) {
            Client.showWindow(200, 400, bundle.getString("Server is tired. Try to reconnect later"), Color.RED);
        }
    }

    public void getWindow(String field, String commandName) {
        Label label = new Label(field);
        TextField textField = new TextField();
        //final String[] arg = new String[1];
        textField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int id = checkId(textField.getText());
                if (id == -1) {
                    Client.showWindow(150, 200, bundle.getString("Incorrect id"), Color.RED);
                } else {
                    List<Object> args = new ArrayList<>();
                    args.add(id);
                    send(new Data(commandName, args, Client.getLogin(), Client.getPassword()), 200, 400);
                }
                getStage.close();
            }
        });
        label.setFont(new Font(20));
        VBox vBox = new VBox(label, textField);
        vBox.setSpacing(25);
        BorderPane pane = new BorderPane(vBox);
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinHeight(100);
        stage.setHeight(200);
        stage.setMinWidth(150);
        stage.setWidth(300);
        getStage = stage;
        stage.show();
    }

    public void getScriptWindow(String field) {
        Label label = new Label(field);
        TextField textField = new TextField();
        //final String[] arg = new String[1];
        textField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FileProcessor processor = new FileProcessor(textField.getText(), new TreeSet<>());
                    List<Data> coms = processor.readData();
                    for (Data com : coms) {
                        Client.sendCommand(com);
                    }
                    Client.showWindow(150, 300, bundle.getString("Script executed"), Color.GREEN);
                } catch (IOException e) {
                    Client.showWindow(150, 400, bundle.getString("Problems with the file which you enter"), Color.RED);
                } catch (RecursiveScript recursiveScript) {
                    Client.showWindow(150, 300, bundle.getString("Error! Recursive in script"), Color.RED);
                }
                getStage.close();
            }
        });
        label.setFont(new Font(20));
        VBox vBox = new VBox(label, textField);
        vBox.setSpacing(25);
        BorderPane pane = new BorderPane(vBox);
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinHeight(100);
        stage.setHeight(200);
        stage.setMinWidth(150);
        stage.setWidth(300);
        getStage = stage;
        stage.show();
    }

    public void getUpdateWindow(String field) {
        Label label = new Label(field);
        TextField textField = new TextField();
        //final String[] arg = new String[1];
        textField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int id = checkId(textField.getText());
                if (id == -1) {
                    Client.showWindow(150, 200, bundle.getString("Incorrect id"), Color.RED);
                } else {
                    GetTicketController.setBundle(bundle);
                    GetTicketController.setCommandName("update");
                    GetTicketController.setPrevWindow("/client/gui/scenes/commands.fxml");
                    GetTicketController.addArg(id);
                    GetTicketController.setStage(Client.changeWindow("/client/gui/scenes/ticket.fxml", startStage, 400, 300));
                }
                getStage.close();
            }
        });
        label.setFont(new Font(20));
        VBox vBox = new VBox(label, textField);
        vBox.setSpacing(25);
        BorderPane pane = new BorderPane(vBox);
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinHeight(100);
        stage.setHeight(200);
        stage.setMinWidth(150);
        stage.setWidth(300);
        getStage = stage;
        stage.show();
    }

    int checkId(String data) {
        try {
            return Integer.parseInt(data);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @FXML
    void maxByComment(ActionEvent event) {
        send(new Data("max_by_comment", null, Client.getLogin(), Client.getPassword()), 400, 970);
    }

    @FXML
    void info(ActionEvent event) {
        send(new Data("info", null, Client.getLogin(), Client.getPassword()), 200, 400);
    }

    @FXML
    void printUniquePrice(ActionEvent event) {
        send(new Data("print_unique_price", null, Client.getLogin(), Client.getPassword()), 200, 400);
    }

    @FXML
    void help(ActionEvent event) {
        send(new Data("help", null, Client.getLogin(), Client.getPassword()), 400, 850);
    }

    @FXML
    void show(ActionEvent event) {
        send(new Data("show", null, Client.getLogin(), Client.getPassword()), 400, 970);
    }

    @FXML
    void removeById(ActionEvent event) {
        getWindow("id", "remove_by_id");
    }

    @FXML
    void removeGreater(ActionEvent event) {
        GetTicketController.setTicket(null);
        GetTicketController.setBundle(bundle);
        GetTicketController.setCommandName("remove_greater");
        GetTicketController.setPrevWindow("/client/gui/scenes/commands.fxml");
        GetTicketController.setStage(Client.changeWindow("/client/gui/scenes/ticket.fxml", startStage, 400, 300));
    }

    @FXML
    void update(ActionEvent event) {
        getUpdateWindow("id");
    }

    @FXML
    void sumOfDiscount(ActionEvent event) {
        send(new Data("sum_of_discount", null, Client.getLogin(), Client.getPassword()), 100, 200);
    }

    @FXML
    void clear(ActionEvent event) {
        send(new Data("clear", null, Client.getLogin(), Client.getPassword()), 400, 300);
    }

    @FXML
    void changeUser(ActionEvent event) {
        StartWindowController.setBundle(bundle);
        StartWindowController.setStage(Client.changeWindow("/client/gui/scenes/start.fxml", startStage, 435, 100));
    }

    @FXML
    public void visualize(ActionEvent event) {
        try {
            VisualizeController.setBundle(bundle);
            VisualizeController.setTickets(Client.sendCommand(new Data("get", null, Client.getLogin(), Client.getPassword())));
            VisualizeController.setStartStage(Client.changeWindow("/client/gui/scenes/visualize.fxml", startStage, 800, 800));
        } catch (IOException e) {
            Client.showWindow(200, 400, bundle.getString("Server is tired. Try to reconnect later"), Color.RED);
        }
    }

    @FXML
    public void openTable(ActionEvent event)  {
        try {
            TableController.setBundle(bundle);
            TableController.setTickets(Client.sendCommand(new Data("get", null, Client.getLogin(), Client.getPassword())));
            TableController.setStage(Client.changeWindow("/client/gui/scenes/table.fxml", startStage, 500, 1000));
            //System.out.println(Client.sendCommand(new Data("get", null, Client.getLogin(), Client.getPassword())).toString());
        } catch (IOException e) {
            Client.showWindow(200, 400, bundle.getString("Server is tired. Try to reconnect later"), Color.RED);
        }
    }

    public void executeScript(ActionEvent event) {
        getScriptWindow("File name");
    }
}

