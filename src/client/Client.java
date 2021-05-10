package client;

import data.Data;
import exceptions.CommandNotFoundException;
import exceptions.RecursiveScript;
import processor.ConsoleProcessor;
import processor.FileProcessor;
import processor.Processor;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {
    private static SocketChannel socket;
    private static boolean firstReconnect = true;
    private static Data lastData = null;
    private static boolean authorization = false;
    public static String login;
    private static String password;

    public static String getLogin() {
        return login;
    }

    public static String getPassword() {
        return password;
    }

    public static void main(String[] args) {
        try {
            socket = SocketChannel.open();
            socket.connect(new InetSocketAddress("localhost", 13345));
            ConsoleProcessor consoleProcessor = new ConsoleProcessor();
            ByteBuffer buffer = ByteBuffer.allocate(65536);
            String ans = "notFirstReconnect";
            if (!firstReconnect) {
                buffer.clear();
                buffer.put(serialize(lastData));
                buffer.flip();
                socket.write(buffer);
                buffer.clear();
                socket.read(buffer);
                ans = deserialize(buffer.array());
                buffer.clear();
                System.out.println(ans);
            }
            if (!ans.equals("exit")) {
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

        } catch (SocketException e) {
            if (firstReconnect) {
                System.out.println("The server is tired. Try to reconnect later");
                firstReconnect = false;
            }
            if (lastData != null) main(args);
        } catch (IOException e) {
            e.printStackTrace();
            main(args);
        }
    }

//    public static void authorization(Processor processor) throws IOException {
//        Scanner scanner = new Scanner(System.in);
//        String data;
//        while (true) {
//            System.out.println("Do you want register or authorization");
//            data = scanner.nextLine();
//            if (data.equals("register") || data.equals("authorization")) {
//                break;
//            }
//            System.out.println("Incorrect data");
//        }
//        sendCommands(processor)
//        ByteBuffer buffer = ByteBuffer.allocate(65536);
//        buffer.put(serialize(processor.getLogin()));
//        buffer.put(serialize(processor.getPassword()));
//        buffer.flip();
//        socket.write(buffer);
//        buffer.clear();
//        socket.read(buffer);
//        String ans = deserialize(buffer.array());
//        if (ans != "Doesn't find this user") {
//            authorization = true;
//            System.out.println("Client connected to server");
//        } else {
//            System.out.println(ans);
//        }
//    }

    public static boolean sendCommands(Processor processor) throws CommandNotFoundException {
        Data data = null;
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
            firstReconnect = false;
            lastData = data;
            main(null);
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

    private static String deserialize(byte[] buffer) throws ClassCastException {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return (String) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Deserialize error");
        }
        return "POISON PILL";
    }
}
