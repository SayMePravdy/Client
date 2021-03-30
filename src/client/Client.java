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
import java.util.List;

public class Client {
    private final static String path = System.getenv().get("LAB6");
    private static SocketChannel socket;
    private static boolean firstReconnect = true;
    private static Data lastData = null;

    public static void main(String[] args) {
        try {
            socket = SocketChannel.open();
            socket.connect(new InetSocketAddress("localhost", 13345));
            ByteBuffer buffer = ByteBuffer.allocate(65536);
            if (path != null) {
                buffer.put(serialize(new File(path)));
            } else {
                buffer.put(serialize(new File("SavedFile")));
            }
            buffer.flip();
            socket.write(buffer);
            buffer.clear();
            System.out.println("Client connected to server");
            socket.read(buffer);
            System.out.println(deserialize(buffer.array()));
            String ans = "";
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
                ConsoleProcessor consoleProcessor = new ConsoleProcessor();
                while (true) {
                    try {
                        if (sendCommands(consoleProcessor)) {
                            break;
                        }
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
            main(args);
        } catch (IOException e) {
            e.printStackTrace();
            main(args);
        }
    }

    public static boolean sendCommands(Processor processor) throws CommandNotFoundException {
        Data data = null;
        try {
            for (Data d : processor.readData()) {
                data = d;
                ByteBuffer buffer = ByteBuffer.allocate(65536);
                buffer.put(serialize(d));
                buffer.flip();
                socket.write(buffer);
                buffer.clear();
                socket.read(buffer);
                String ans = deserialize(buffer.array());
                buffer.clear();
                if (ans.equals("exit")) {
                    return true;
                }
                System.out.println(ans);
            }
        } catch (IOException | ClassCastException e) {
            System.out.println("Server is closed");
            lastData = data;
            main(null);
            return true;
        }
        return false;
    }



    private static <T> byte[] serialize(T data) {
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)){
            objectOutputStream.writeObject(data);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            System.out.println("Serialize problem");
        }
        return null;
    }

    private static String deserialize(byte [] buffer) throws ClassCastException{
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return (String) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Deserialize error");
        }
        return "POISON PILL";
    }
}
