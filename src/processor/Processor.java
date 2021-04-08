package processor;

import data.Data;
import data.Event;
import data.Ticket;
import data.TicketType;
import exceptions.CommandNotFoundException;
import exceptions.InvalidArgument;
import exceptions.NullTicketArgument;

import java.io.File;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import static data.Resources.*;

/**
 * Класс оболочка для считывания и обработки данных
 */

public abstract class Processor {
    protected String ticketName, eventName, comment;
    protected float price, y;
    protected long discount;
    protected int minAge;
    protected double x;
    protected Integer ticketsCount;
    protected TicketType ticketType = null;
    protected Event event = null;
    protected boolean exit = false;
    protected NavigableSet<File> history = new TreeSet<>();

    public abstract Ticket getTicket();
    public abstract NavigableSet<File> getHistory();
    public abstract int getId();
    public abstract String getName();
    public abstract List<Data> readData() throws CommandNotFoundException;
    public abstract String getLogin();
    public abstract String getPassword();

    public boolean isExit() {
        return exit;
    }

    int checkId(String data) {
        try {
            int id = Integer.parseInt(data);
            return id;
        } catch (NumberFormatException e) {
            return -1;
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
}
