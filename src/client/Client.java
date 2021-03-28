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

    public static void main(String[] args) {
        try {
            socket = SocketChannel.open();
            socket.connect(new InetSocketAddress("localhost", 3345));
            ByteBuffer buffer = ByteBuffer.allocate(65536);
            buffer.put(serialize(new File(path)));
            buffer.flip();
            socket.write(buffer);
            buffer.clear();
            boolean stop = false;
            System.out.println("Client connected to server");
            if (path != null) {
                FileProcessor fileProcessor = null;
                try {
                    fileProcessor = new FileProcessor(path, null);
                    List<Data> tickets = fileProcessor.readDataFromCsv();
                    try {
                        for (Data d : tickets) {
                            //ByteBuffer buffer = ByteBuffer.allocate(65536);
                            buffer.put(serialize(d));
                            buffer.flip();
                            socket.write(buffer);
                            buffer.clear();
                            socket.read(buffer);
                            System.out.println(deserialize(buffer.array()));
                            buffer.clear();
                        }
                    } catch (IOException e) {
                        System.out.println("Server is ill. Try to reconnect later");
                        stop = true;
                    }

                } catch (IOException e) {
                    System.out.println("Something wrong with file: " + path);
                } catch (RecursiveScript e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("File not found");
            }

            if (!stop) {
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
            System.out.println("The server is tired. Try to reconnect later");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean sendCommands(Processor processor) throws CommandNotFoundException {
        try {
            for (Data d : processor.readData()) {
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
        } catch (IOException e) {
            System.out.println("Server is closed");
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

    private static String deserialize(byte [] buffer) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return (String) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Deserialize error");
        }
        return "POISON PILL";
    }
}
