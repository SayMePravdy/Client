package client.gui.controllers;

import client.Client;
import data.Data;
import data.Ticket;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class VisualizeController {

    private static Stage startStage;

    private Thread thread;

    private static List<Ticket> tickets;

    public static void setTickets(List<Ticket> tickets) {
        VisualizeController.tickets = tickets;
    }

    public static void setBundle(ResourceBundle bundle) {
        VisualizeController.bundle = bundle;
    }

    //private int langNum;
    private static ResourceBundle bundle;

    public static void setStartStage(Stage startStage) {
        VisualizeController.startStage = startStage;
    }

    @FXML
    private GridPane field;

    @FXML
    private Button backButton;

    @FXML
    void back(ActionEvent event) {
        thread.interrupt();
        CommandsWindowController.setStartStage(Client.changeWindow("/client/gui/scenes/commands.fxml", startStage, 400, 530));
    }

    private boolean compare(List<Ticket> list1, List<Ticket> list2) {
        if (list1.size() != list2.size())
            return false;
        for (int i = 0; i < list1.size(); i++) {
            boolean check = false;
            for (int j = 0; j < list1.size(); j++) {
                if (list1.get(i).equals(list2.get(j))) {
                    check = true;
                    break;
                }
            }
            if (!check) return false;
        }
        return true;
    }

    @FXML
    public void initialize() {
        showTickets();
        thread = new Thread(() -> {
            try {
                while (true) {
                    List<Ticket> list = Client.sendCommand(new Data("get", null, Client.getLogin(), Client.getPassword()));
                    if (!compare(list, tickets)) {
                        tickets = list;
                        Platform.runLater(this::showTickets);
                    }
                    Thread.sleep(500);
                }
            } catch(InterruptedException e) {
            } catch (IOException e) {
            }
        });
        thread.start();

        //new Thread()
    }

    public static String convertStringToHex(String str) {

        StringBuffer hex = new StringBuffer();

        // loop chars one by one
        for (char temp : str.toCharArray()) {

            // convert char to int, for char `a` decimal 97
            int decimal = (int) temp;

            // convert int to hex, for decimal 97 hex 61
            hex.append(Integer.toHexString(decimal));
        }

        return hex.toString();

    }

    private void showTickets() {
        field.getChildren().clear();
        List<ViewTicket> viewTickets = getPositions();
        Map<Rectangle, Ticket> view = new HashMap<>();

        for (int i = 0; i < viewTickets.size(); i++) {
            Rectangle rectangle = new Rectangle();
            rectangle.setX(20);
            rectangle.setY(10);
            rectangle.setWidth(100);
            rectangle.setHeight(50);

            String hex = convertStringToHex(viewTickets.get(i).getTicket().getUser());
            hex = (hex.length() > 6 ? hex.substring(0, 6) : hex);
            while (hex.length() < 6) {
                hex += "0";
            }

            Color color = Color.web("#" + hex);
            rectangle.setFill(color);
            Text text = new Text(viewTickets.get(i).getTicket().getName());
            text.setFill(Color.WHITE);

            int finalI = i;
            rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    List<Object> arg = new ArrayList<>();
                    arg.add(viewTickets.get(finalI).getTicket().getId());
                    try {
                        String ans = Client.sendCommand(new Data("findId", arg, Client.getLogin(), Client.getPassword()));
                        if (ans.equals("true")) {
                            GetTicketController.setCommandName("update");
                            GetTicketController.setBundle(bundle);
                            GetTicketController.setPrevWindow("/client/gui/scenes/visualize.fxml");
                            GetTicketController.addArg(viewTickets.get(finalI).getTicket().getId());
                            GetTicketController.setTicket(viewTickets.get(finalI).getTicket());
                            GetTicketController.setStage(Client.changeWindow("/client/gui/scenes/ticket.fxml", startStage, 400, 500));
                        } else {
                            Client.showWindow(200, 500, bundle.getString(ans), Color.BLACK);
                        }
                    } catch (IOException e) {
                        //e.printStackTrace();
                        Client.showWindow(200, 500, bundle.getString("Server is tired. Try to reconnect later"), Color.RED);
                    }
                }
            });
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(1000));
            scaleTransition.setNode(rectangle);
            //scaleTransition.setNode(text);
            scaleTransition.setCycleCount(50);
            scaleTransition.setByX(0.25);
            scaleTransition.setByY(0.25);
            scaleTransition.setAutoReverse(true);
            scaleTransition.play();
            StackPane pane = new StackPane();
            pane.getChildren().addAll(rectangle, text);
            //view.put(rectangle, viewTickets.get(i).getTicket());
            field.add(pane, viewTickets.get(i).getX(), viewTickets.get(i).getY());
        }
    }

    private List<ViewTicket> getPositions() {
        List<Ticket> sortX = tickets.stream().sorted((t1, t2) -> Double.compare(t1.getX(), t2.getX())).collect(Collectors.toList());
        List<Ticket> sortY = tickets.stream().sorted((t1, t2) -> Float.compare(t1.getY(), t2.getY())).collect(Collectors.toList());
        List<ViewTicket> view = new ArrayList<>();
        for (int i = 0; i < tickets.size(); i++) {
            Ticket ticket = tickets.get(i);
            view.add(new ViewTicket(find(sortX, ticket), find(sortY, ticket), ticket));
        }
        return view;
    }

    private int find(List<Ticket> tickets, Ticket ticket) {
        for (int i = 0; i < tickets.size(); i++) {
            if (tickets.get(i).equals(ticket)) return i;
        }
        return 0;
    }

    class ViewTicket {
        int x;
        int y;
        Ticket ticket;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public ViewTicket(int x, int y, Ticket ticket) {
            this.x = x;
            this.y = y;
            this.ticket = ticket;
        }

        public Ticket getTicket() {
            return ticket;
        }
    }

    ;

}
