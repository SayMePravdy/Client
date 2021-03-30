package processor;

import client.CommandManager;
import data.Data;
import data.Coordinates;
import data.Event;
import data.Ticket;
import exceptions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;

import static data.Resources.*;

/**
 * Класс, отвечающий за работу с входными данными из файла
 */

public class FileProcessor extends Processor {
    final private String path;

    public NavigableSet<File> getHistory() {
        return history;
    }

    public void setParent(NavigableSet history) {
        this.history = history;
    }

    //private boolean script;
    private String[] dataFile;
    private int lineNum = 0;
    //private boolean exit = false;

    public FileProcessor(String path, NavigableSet<File> history) throws IOException, RecursiveScript {
        this.path = path;
        if (history != null) {
            this.history.addAll(history);
            if (this.history.contains(new File(path))) {
                throw new RecursiveScript("Error! Script call each other");
            }
        }
        this.history.add(new File(path));
        //this.script = script;
        dataFile = read(path).split("\n");
        for (int i = 0; i < dataFile.length; i++) {
            dataFile[i] = dataFile[i].replace("\r", "");
        }
    }


    private String read(String path) throws IOException {
        StringBuilder dataFile = new StringBuilder();

        InputStreamReader reader = new InputStreamReader(new FileInputStream(path));
        int x = reader.read();
        while (x != -1) {
            dataFile.append((char) x);
            x = reader.read();
        }

        reader.close();
        return dataFile.toString();
    }

    @Override
    public List<Data> readData() {
        List<Data> coms = new ArrayList<>();
        while (lineNum < dataFile.length) {
            try {
                CommandManager commandManager = new CommandManager(dataFile[lineNum++], this);
                coms.addAll(commandManager.getCommands());
            } catch (CommandNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("File not found");
            }
        }
        return coms;
    }


    @Override
    public Ticket getTicket() {
        int skip = 0;
        boolean mayNull = false;
        try {
            checkName(dataFile[lineNum]);
            event = null;
            ticketType = null;
            ticketName = dataFile[lineNum++];
            x = Double.parseDouble(dataFile[lineNum++]);
            y = checkY(dataFile[lineNum++]);
            price = checkPrice(dataFile[lineNum++]);
            discount = checkDiscount(dataFile[lineNum++]);
            checkComment(dataFile[lineNum++]);
            skip++;
            mayNull = true;
            ticketType = checkTicketType(dataFile[lineNum++]);
            checkName(dataFile[lineNum]);
            eventName = dataFile[lineNum++];
            skip++;
            minAge = Integer.parseInt(dataFile[lineNum++]);
            skip++;
            ticketsCount = checkTicketsCount(dataFile[lineNum++]);
            event = new Event(0, eventName, minAge, ticketsCount);
        } catch (NullTicketArgument | ArrayIndexOutOfBoundsException e) {
            //nullTicketArgument.printStackTrace();
            lineNum -= skip;
            return new Ticket(0, ticketName, new Coordinates(x, y), ZonedDateTime.now(), price, discount, comment, ticketType, event);
        } catch (InvalidArgument e) {
            return null;
        } catch (NumberFormatException e) {
            if (mayNull) {
                lineNum -= skip;
                return new Ticket(0, ticketName, new Coordinates(x, y), ZonedDateTime.now(), price, discount, comment, ticketType, event);
            } else {
                return null;
            }
        }
        return new Ticket(0, ticketName, new Coordinates(x, y), ZonedDateTime.now(), price, discount, comment, ticketType, event);
    }

    @Override
    public int getId() {
        try {
            int id = Integer.parseInt(dataFile[lineNum++]);
            return id;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return -1;
        }
    }

    @Override
    public String getName() {
        try {
            return dataFile[lineNum++];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error");
        }
        return "";
    }

}
