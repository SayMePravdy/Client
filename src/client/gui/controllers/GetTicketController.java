package client.gui.controllers;

import client.Client;
import data.*;
import data.Event;
import exceptions.InvalidArgument;
import exceptions.NullTicketArgument;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static data.Resources.*;

public class GetTicketController {

    private ObservableList<String> ticketTypes = FXCollections.observableArrayList("VIP", "USUAL", "CHEAP", "BUDGETARY");
    private static Stage startStage;
    private static String commandName;
    private static List<Object> args = new ArrayList<>();

    public static void setStage(Stage startStage) {
        GetTicketController.startStage = startStage;
    }

    @FXML
    private CheckBox hasEvent;

    @FXML
    private TextField ticketsCount;

    @FXML
    private TextField price;

    @FXML
    private TextField minAge;

    @FXML
    private TextField name;

    @FXML
    private TextField x;

    @FXML
    private TextField y;

    @FXML
    private TextField discount;

    @FXML
    private TextField eventName;

    @FXML
    private TextField comment;

    @FXML
    private ChoiceBox<String> ticketType;

    public static void setCommandName(String name) {
        commandName = name;
    }

    public static void addArg(Object arg) {
        args.add(arg);
    }


    TicketType checkTicketType(String data) throws NullTicketArgument {
        switch (data) {
            case "VIP":
                return TicketType.VIP;
            case "USUAL":
                return TicketType.USUAL;
            case "BUDGETARY":
                return TicketType.BUDGETARY;
            case "CHEAP":
                return TicketType.CHEAP;
            default:
                throw new NullTicketArgument("Could not find this ticket type");
        }
    }

    void checkName(String data) throws NullTicketArgument {
        if (data.isEmpty()){
            throw new NullTicketArgument("Incorrect name");
        }
    }

    float checkY(String data) throws InvalidArgument, NumberFormatException {
        float y = Float.parseFloat(data);
        if (y > MAX_Y) {
            throw new InvalidArgument(String.format("y-coordinate must be less than %f", MAX_Y));
        }
        return y;
    }

    float checkPrice(String data) throws InvalidArgument, NumberFormatException {
        float price = Float.parseFloat(data);
        if (price <= MIN) {
            throw new InvalidArgument(String.format("Price must be more then %d", MIN));
        }
        return price;
    }

    long checkDiscount(String data) throws InvalidArgument, NumberFormatException {
        long discount = Long.parseLong(data);
        if (discount <= MIN || discount > MAX_DISCOUNT) {
            throw new InvalidArgument(String.format("Discount can be from %d to %d", MIN, MAX_DISCOUNT));
        }
        return discount;
    }

    void checkComment(String data) throws InvalidArgument {
        if (data.isEmpty()) {
            throw new InvalidArgument("Incorrect comment");
        }

    }

    int checkTicketsCount(String data) throws NumberFormatException, NullTicketArgument {
        int ticketsCount = Integer.parseInt(data);
        if (ticketsCount <= MIN) {
            throw new NullTicketArgument(String.format("Tickets count must be more than %d", MIN));
        }
        return ticketsCount;
    }

    String getName(ActionEvent event) throws NullTicketArgument {
        String ticketName = name.getText();
        checkName(ticketName);
        return ticketName;
    }

    double getX(ActionEvent event) throws NumberFormatException{
        return Double.parseDouble(x.getText());
    }

    float getY(ActionEvent event) throws InvalidArgument, NumberFormatException {
        return checkY(y.getText());
    }

    float getPrice(ActionEvent event) throws InvalidArgument, NumberFormatException {
        return checkPrice(price.getText());
    }

    long getDiscount(ActionEvent event) throws InvalidArgument, NumberFormatException {
        return checkDiscount(discount.getText());
    }

    String getComment(ActionEvent event) throws InvalidArgument {
        String comment = this.comment.getText();
        checkComment(comment);
        return comment;
    }

    String getEventName(ActionEvent event) throws NullTicketArgument {
        String name = eventName.getText();
        checkName(name);
        return name;
    }

    int getMinAge(ActionEvent event) throws NumberFormatException{
        return Integer.parseInt(minAge.getText());
    }

    Integer getTicketsCount(ActionEvent event) throws NullTicketArgument, NumberFormatException {
        return checkTicketsCount(ticketsCount.getText());
    }

    @FXML
    void initialize() {
        ticketType.setItems(ticketTypes);
    }

    @FXML
    public void back(ActionEvent event) {
        CommandsWindowController.setStartStage(Client.changeWindow("/client/gui/scenes/commands.fxml", startStage, 400, 530));
    }

    @FXML
    public void enter(ActionEvent event) {
        //List<Integer> wrongArguments = new ArrayList<>();
        StringBuilder errors = new StringBuilder();
        String name = null;
        double x = 0;
        float y = 0;
        float price = 0;
        long discount = 0;
        String comment = null;
        TicketType type = null;
        String eventName = null;
        int minAge = 0;
        Integer ticketsCount = null;
        Event ticketEvent = null;

        try {
            name = getName(event);
        } catch (NullTicketArgument e) {
            errors.append("Name: " + e.getMessage() + "\n");
        }

        try {
            x = getX(event);
        } catch (NumberFormatException e) {
            errors.append("X: Please enter a double argument\n");
        }

        try {
            y = getY(event);
        } catch (InvalidArgument e) {
            errors.append("Y: " + e.getMessage() + "\n");
        } catch (NumberFormatException e) {
            errors.append("Y: Please enter a float argument\n");
        }

        try {
            price = getPrice(event);
        } catch (InvalidArgument e) {
            errors.append("Price: " + e.getMessage() + "\n");
        } catch (NumberFormatException e) {
            errors.append("Price: Please enter a float argument\n");
        }

        try {
            discount = getDiscount(event);
        } catch (InvalidArgument e) {
            errors.append("Discount: " + e.getMessage() + "\n");
        } catch (NumberFormatException e) {
            errors.append("Discount: Please enter a long argument\n");
        }

        try {
            comment = getComment(event);
        } catch (InvalidArgument e) {
            errors.append("Comment: " + e.getMessage() + "\n");
        }

        try {
            type = checkTicketType(ticketType.getValue());
        } catch (NullTicketArgument | NullPointerException nullTicketArgument) {
        }

        if (hasEvent.isSelected()) {
            try {
                eventName = getEventName(event);
            } catch (NullTicketArgument e) {
                errors.append("Event Name: " + e.getMessage() + "\n");
            }

            try {
                minAge = getMinAge(event);
            } catch (NumberFormatException e) {
                errors.append("Discount: Please enter a long argument\n");
            }

            try {
                ticketsCount = getTicketsCount(event);
            } catch (NumberFormatException e) {
                errors.append("Tickets Count: Please enter a long argument\n");
            } catch (NullTicketArgument e) {
                errors.append("Tickets Count: " + e.getMessage() + "\n");
            }

            ticketEvent = new Event(1, eventName, minAge, ticketsCount);
        }

        if (errors.length() == 0) {
            args.add(new Ticket(1, name, new Coordinates(x, y), ZonedDateTime.now(), price, discount, comment, type, ticketEvent));
            Data data = new Data(commandName, args, Client.getLogin(), Client.getPassword());
            System.out.println(data);
            try {
                String ans = Client.sendCommand(data);
                CommandsWindowController.setStartStage(Client.changeWindow("/client/gui/scenes/commands.fxml", startStage, 450, 530));
                Client.showWindow(200, 400, ans, Color.GREEN);
            } catch (IOException e) {
                Client.showWindow(200, 400, "Server is tired. Try to reconnect later", Color.RED);
            }
        } else {
            Client.showWindow(400, 500, errors.toString(), Color.RED);
        }
        args.clear();
    }
}
