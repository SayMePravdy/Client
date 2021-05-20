package client;

import client.gui.controllers.StartWindowController;
import data.Data;
import data.Ticket;
import exceptions.CommandNotFoundException;
import exceptions.RecursiveScript;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import processor.ConsoleProcessor;
import processor.FileProcessor;
import processor.Processor;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Scanner;

public class Client extends Application {
    private static SocketChannel socket;
    private static boolean authorization = false;
    private static String login;
    private static String password = "";
    private static ArrayList<Ticket> tickets;

    public static String getLogin() {
        return login;
    }

    public static String getPassword() {
        return password;
    }

    public static void main(String[] args) {
        try {
            //socket = SocketChannel.open();
            //socket.connect(new InetSocketAddress("localhost", 13345));
            launch(args);
            start();
        } catch (SocketException e) {
            System.out.println("The server is tired. Try to reconnect later");
        } catch (IOException e) {
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        StartWindowController.setStage(stage);
        stage.setMinHeight(435);
        stage.setMinWidth(100);

        FXMLLoader root = new FXMLLoader();
        root.setLocation(getClass().getResource("/client/gui/scenes/start.fxml"));
        Parent xml = root.load();
        Scene scene = new Scene(xml);


        stage.setScene(scene);

        //stage.setTitle("Hello JavaFX");
        stage.setWidth(700);
        stage.setHeight(500);

        stage.show();
    }

    public static void setLogin(String login) {
        Client.login = login;
    }

    public static void setPassword(String password) {
        Client.password = password;
    }

    public static void showWindow(double height, double width, String msg, Color color) {
        Label label = new Label(msg);
        label.setTextFill(color);
        label.setFont(new Font(20));
        BorderPane pane = new BorderPane(label);
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinWidth(width);
        stage.setMinHeight(height);
        stage.setHeight(height);
        stage.setWidth(width);
        stage.show();
    }

    public static Stage changeWindow(String window, Stage startStage, double minHeight, double minWidth) {
        try {
            FXMLLoader root = new FXMLLoader();
            root.setLocation(Client.class.getResource(window));
            Parent xml = root.load();
            Scene scene = new Scene(xml);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setMinHeight(minHeight);
            stage.setMinWidth(minWidth);
            startStage.close();
            stage.show();
            return stage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void start() throws IOException {
        ConsoleProcessor consoleProcessor = new ConsoleProcessor();
        while (true) {
            try {
                if (sendCommands(consoleProcessor)) {
                    break;
                }
                socket = SocketChannel.open();
                socket.connect(new InetSocketAddress("localhost", 13345));
            } catch (CommandNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static <T> T sendCommand(Data data) throws IOException {
        socket = SocketChannel.open();
        socket.connect(new InetSocketAddress("localhost", 13345));
        ByteBuffer buffer = ByteBuffer.allocate(65536);
        buffer.put(serialize(data));
        buffer.flip();
        socket.write(buffer);
        buffer.clear();
        socket.read(buffer);
        return deserialize(buffer.array());
    }

    public static boolean sendCommands(Processor processor) throws CommandNotFoundException {
        Data data;
        try {
            for (Data d : processor.readData()) {
                data = d;
                if (authorization || data.getCommandName().equals("authorization") || data.getCommandName().equals("register")) {
                    if (d.getCommandName().equals("exit")) {
                        return true;
                    }
                    ByteBuffer buffer = ByteBuffer.allocate(65536);
                    buffer.put(serialize(d));
                    buffer.flip();
                    socket.write(buffer);
                    buffer.clear();
                    socket.read(buffer);
                    String ans = deserialize(buffer.array());
                    if (!(ans.equals("Your data is too long") || ans.equals("User with this login already exists") || ans.equals("Doesn't found this user"))) {
                        authorization = true;
                        login = data.getLogin();
                        password = data.getPassword();
                    }
                    buffer.clear();
                    System.out.println(ans);

                    if (ans.contains("exit")) {
                        socket = SocketChannel.open();
                        socket.connect(new InetSocketAddress("localhost", 13345));
                        return true;
                    }
                }else {
                    System.out.println("Please authorization or register user");
                    return false;
                }
            }
        } catch (IOException | ClassCastException e) {
            System.out.println("Server is closed. Try to reconnect...");
            return true;
        }
        return false;
    }


    private static <T> byte[] serialize(T data) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(data);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            System.out.println("Serialize problem");
        }
        return null;
    }

    private static<T> T deserialize(byte[] buffer) throws ClassCastException {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return (T) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Deserialize error");
        }
        return (T)"POISON PILL";
    }

}
