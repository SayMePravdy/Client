package processor;

import client.Client;
import client.CommandManager;
import data.Data;
import data.Coordinates;
import data.Event;
import data.Ticket;
import data.TicketType;
import exceptions.CommandNotFoundException;
import exceptions.InvalidArgument;
import exceptions.NullTicketArgument;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.Scanner;

import static data.Resources.*;

/**
 * Класс отвечающий за работу с данными из консоли
 */
public class ConsoleProcessor extends Processor {

    @Override
    public List<Data> readData() throws CommandNotFoundException {
        Scanner scanner = new Scanner(System.in);
        if (Client.login != null) {
            System.out.println("Enter a command");
        } else{
            System.out.println("Please authorization or register");
        }
        String command = scanner.nextLine().trim();
        List<Data> com = new ArrayList<>();
        try {
            CommandManager commandManager = new CommandManager(command, this);
            com.addAll(commandManager.getCommands());
        } catch (IOException e) {
            System.out.println("File not found");
        }
        return com;
    }

    @Override
    public String getLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter login");
        String login = scanner.nextLine();
        while (login.isEmpty()) {
            System.out.println("Login can't be empty");
            login = scanner.nextLine();
        }
        return login;
    }

    @Override
    public String getPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter password");
        return scanner.nextLine();
    }

    @Override
    public Ticket getTicket() {

        Scanner scanner = new Scanner(System.in);

        ticketName = getTicketName(scanner);
        x = getX(scanner);
        y = getY(scanner);

        price = getPrice(scanner);

        discount = getDiscount(scanner);

        comment = getComment(scanner);

        ticketType = getTicketType(scanner);

        event = getEvent(scanner);


        return new Ticket(0, ticketName, new Coordinates(x, y), ZonedDateTime.now(), price, discount, comment, ticketType, event);
    }

    @Override
    public NavigableSet<File> getHistory() {
        return null;
    }

    private TicketType getTicketType(Scanner scanner) {
        String data;
        TicketType ticketType = null;
        System.out.println("Enter ticket type. Choose one of:VIP, USUAL, BUDGETARY, CHEAP. Don't enter anything if you don't want");
        data = scanner.nextLine();
        if (!data.isEmpty()) {
            while (true) {
                try {
                    ticketType = checkTicketType(data);
                    break;
                } catch (NullTicketArgument e){
                    System.out.println(e.getMessage());
                }
                data = scanner.nextLine();
            }

        }
        return ticketType;
    }

    private Event getEvent(Scanner scanner) {
        String data;
        Event event = null;
        String eventName = "";
        int minAge, ticketsCount;
        System.out.println("Do you want to enter an event? Enter yes or no?");
        data = scanner.nextLine();
        while (!data.equals("yes") && !data.equals("no")){
            System.out.println("Incorrect data");
            data = scanner.nextLine();
        }
        if (data.equals("yes")) {

            System.out.println("Enter event name");
            while (true) {
                eventName = scanner.nextLine();
                try {
                    checkName(eventName);
                    break;
                }catch (NullTicketArgument e){
                    System.out.println(e.getMessage());
                }
            }

            System.out.println("Enter minimal age");
            while (true) {
                data = scanner.nextLine();
                try {
                    minAge = Integer.parseInt(data);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect data. Please enter int argument");
                }
            }

            System.out.println("Enter tickets count");
            while (true) {
                data = scanner.nextLine();
                try {
                    ticketsCount = checkTicketsCount(data);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect data. Please enter int argument");
                } catch (NullTicketArgument e) {
                    System.out.println(e.getMessage());
                }
            }

            event = new Event(0, eventName, minAge, ticketsCount);
        }
        return event;
    }

    private String getComment(Scanner scanner) {
        String comment = "";
        System.out.println("Enter comment");
        while (comment.isEmpty()) {
            comment = scanner.nextLine();
            if (comment.isEmpty()) {
                System.out.println("Incorrect comment");
            }
        }
        return comment;
    }

    private long getDiscount(Scanner scanner) {
        String data;
        long discount;
        System.out.println("Enter discount");
        while (true) {
            data = scanner.nextLine();
            try {
                discount = checkDiscount(data);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Incorrect data. Please enter long argument");
            } catch (InvalidArgument e) {
                System.out.println(e.getMessage());
            }
        }
        return discount;
    }

    private float getPrice(Scanner scanner) {
        String data;
        float price;
        System.out.println("Enter ticket price");
        while (true) {
            data = scanner.nextLine();
            try {
                price = checkPrice(data);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Incorrect data. Please enter float argument");
            } catch (InvalidArgument e) {
                System.out.println(e.getMessage());
            }
        }
        return price;
    }

    private float getY(Scanner scanner) {
        float y;
        String data;

        System.out.println("Enter y-coordinate");
        while (true) {
            data = scanner.nextLine();
            try {
                y = checkY(data);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Incorrect data. Please enter float argument");
            } catch (InvalidArgument e) {
                System.out.println(e.getMessage());
            }
        }
        return y;
    }

    private double getX(Scanner scanner) {
        double x;
        String data;

        System.out.println("Enter x-coordinate");
        while (true) {
            data = scanner.nextLine();
            try {
                x = Double.parseDouble(data);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Enter a double argument");
            }
        }
        return x;
    }

    public int getId(){
        System.out.println("Enter id");
        Scanner scanner = new Scanner(System.in);
        int id;
        while (true) {
            id = checkId(scanner.nextLine());
            if (id != -1 ) {
                return id;
            }
            System.out.println("Incorrect data. Please enter int argument");
        }
    }

    public String getName() {
        System.out.println("Enter file name");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }


    private String getTicketName(Scanner scanner) {
        String ticketName = "";

        System.out.println("Enter ticket name");
        while (true) {
            ticketName = scanner.nextLine();
            try {
                checkName(ticketName);
                break;
            } catch (NullTicketArgument e) {
                System.out.println(e.getMessage());
            }
        }
        return ticketName;
    }


}