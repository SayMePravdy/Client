package client.gui.controllers;

import client.Client;
import data.Ticket;
import data.TicketType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TableController {

    private static Stage startStage;
    private static List<Ticket> tickets;

    @FXML
    private TableColumn<Ticket, Integer> ticketsCountColumn;

    @FXML
    private TableColumn<Ticket, String> commentColumn;

    @FXML
    private TableColumn<Ticket, Float> yColumn;

    @FXML
    private TableColumn<Ticket, Integer> idColumn;

    @FXML
    private TableColumn<Ticket, ZonedDateTime> dateColumn;

    @FXML
    private TableColumn<Ticket, Float> priceColumn;

    @FXML
    private TableColumn<Ticket, String> eventNameColumn;

    @FXML
    private TableColumn<Ticket, Double> xColumn;

    @FXML
    private TableColumn<Ticket, Long> discountColumn;

    @FXML
    private TableColumn<Ticket, TicketType> typeColumn;

    @FXML
    private TableColumn<Ticket, Integer> minimalAgeColumn;

    @FXML
    private TableColumn<Ticket, Integer> eventIdColumn;

    @FXML
    private TableColumn<Ticket, String> ticketNameColumn;

    @FXML
    private ChoiceBox<String> field;

    public static void setTickets(List<Ticket> tickets) {
        TableController.tickets = tickets;
    }

    ObservableList<String> fields = FXCollections.observableArrayList("id", "Ticket Name", "X", "Y", "Creation Date", "Price", "Discount", "Comment");

    @FXML
    private Button backButton;


    @FXML
    private TableView<Ticket> table;

    public static void setStage(Stage startStage) {
        TableController.startStage = startStage;
    }

    @FXML
    private Button sortButton;

    @FXML
    private TextField word;

    @FXML
    private Button filterButton;

    @FXML
    void sort(ActionEvent event) {
        ObservableList<Ticket> tableTickets = FXCollections.observableArrayList();
        //removeAllRows();
        if (field.getValue() == null) {
            Client.showWindow(200, 300, "Please select field", Color.BLACK);
        } else {
            table.getItems().removeAll(tickets);
            switch (field.getValue()) {
                case "id":
                    tableTickets.addAll(tickets.stream().sorted(Comparator.comparing(t -> ((Integer) t.getId()))).collect(Collectors.toList()));
                    break;
                case "Ticket Name":
                    tableTickets.addAll(tickets.stream().sorted(Comparator.comparing(t -> ((String) t.getName()))).collect(Collectors.toList()));
                    break;
                case "X":
                    tableTickets.addAll(tickets.stream().sorted(Comparator.comparing(t -> ((Double) t.getX()))).collect(Collectors.toList()));
                    break;
                case "Y":
                    tableTickets.addAll(tickets.stream().sorted(Comparator.comparing(t -> ((Float) t.getY()))).collect(Collectors.toList()));
                    break;
                case "Creation  Date":
                    tableTickets.addAll(tickets.stream().sorted(Comparator.comparing(t -> ((ZonedDateTime) t.getCreationDate()))).collect(Collectors.toList()));
                    break;
                case "Price":
                    tableTickets.addAll(tickets.stream().sorted(Comparator.comparing(t -> ((Float) t.getPrice()))).collect(Collectors.toList()));
                    break;
                case "Discount":
                    tableTickets.addAll(tickets.stream().sorted(Comparator.comparing(t -> ((Long) t.getDiscount()))).collect(Collectors.toList()));
                    break;
                case "Comment":
                    tableTickets.addAll(tickets.stream().sorted(Comparator.comparing(t -> ((String) t.getComment()))).collect(Collectors.toList()));
                    break;
            }
            table.setItems(tableTickets);
        }
    }

//    private void removeAllRows(){
//        for ( int i = 0; i<table.getItems().size(); i++) {
//            table.getItems().clear();
//        }
//    }

    @FXML
    void filter(ActionEvent event) {
        String find = word.getText();
        String fField = field.getValue();
        ObservableList<Ticket> tableTickets = FXCollections.observableArrayList();
        //removeAllRows();
        if (fField == null) {
            Client.showWindow(200, 300, "Please select field", Color.BLACK);
        } else {
            table.getItems().removeAll(tickets);
            switch (fField) {
                case "id":
                    tableTickets.addAll(tickets.stream()
                            .filter(ticket -> String.valueOf(ticket.getId()).contains(find))
                            .collect(Collectors.toList()));
                    break;
                case "Ticket Name":
                    tableTickets.addAll(tickets.stream().filter(ticket -> String.valueOf(ticket.getName()).contains(find)).collect(Collectors.toList()));
                    break;
                case "X":
                    tableTickets.addAll(tickets.stream().filter(ticket -> String.valueOf(ticket.getX()).contains(find)).collect(Collectors.toList()));
                    break;
                case "Y":
                    tableTickets.addAll(tickets.stream().filter(ticket -> String.valueOf(ticket.getY()).contains(find)).collect(Collectors.toList()));
                    break;
                case "Creation  Date":
                    tableTickets.addAll(tickets.stream().filter(ticket -> String.valueOf(ticket.getCreationDate()).contains(find)).collect(Collectors.toList()));
                    break;
                case "Price":
                    tableTickets.addAll(tickets.stream().filter(ticket -> String.valueOf(ticket.getPrice()).contains(find)).collect(Collectors.toList()));
                    break;
                case "Discount":
                    tableTickets.addAll(tickets.stream().filter(ticket -> String.valueOf(ticket.getDiscount()).contains(find)).collect(Collectors.toList()));
                    break;
                case "Comment":
                    tableTickets.addAll(tickets.stream().filter(ticket -> String.valueOf(ticket.getComment()).contains(find)).collect(Collectors.toList()));
                    break;
            }
            table.setItems(tableTickets);
        }
    }

    @FXML
    void back(ActionEvent event) {
        CommandsWindowController.setStartStage(Client.changeWindow("/client/gui/scenes/commands.fxml", startStage, 450, 530));
    }

    @FXML
    void initialize() {
        field.setItems(fields);
        initializeTable();
        loadTickets();
    }

    @FXML
    private void loadTickets() {
        ObservableList<Ticket> tableTickets = FXCollections.observableArrayList();
        tableTickets.addAll(tickets);
        table.setItems(tableTickets);
    }

    private void initializeTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ticketNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        xColumn.setCellValueFactory(new PropertyValueFactory<>("X"));
        yColumn.setCellValueFactory(new PropertyValueFactory<>("Y"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("CreationDate"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("Discount"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("Comment"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("EventId"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("EventName"));
        ticketsCountColumn.setCellValueFactory(new PropertyValueFactory<>("TicketsCount"));
        minimalAgeColumn.setCellValueFactory(new PropertyValueFactory<>("MinAge"));
        //removeAllRows();
    }

    public void update(ActionEvent event) {

    }
}

