package data.resources;

import java.util.ListResourceBundle;

import static data.Resources.MAX_Y;

public class data_en extends ListResourceBundle {
    private static final Object[][] content = {
            // Ticket Fields
            {"language", "English"},
            {"ticket", "Ticket"},
            {"name", "Name"},
            {"coordinates", "Coordinates"},
            {"creationDate", "Creation Date"},
            {"price", "Price"},
            {"discount", "Discount"},
            {"comment", "Comment"},
            {"type", "Type"},
            {"eventId", "Event id"},
            {"eventName", "Event Name"},
            {"ticketCount", "Tickets Count"},
            {"event", "Event"},
            {"minAge", "Minimal Age"},
            //Labels and some buttons
            {"back", "Back"},
            {"table", "Table"},
            {"exit", "Exit"},
            {"enter", "Enter"},
            {"logInLabel", "Log In"},
            {"login", "Login"},
            {"password", "Password"},
            {"logInButton", "Enter"},
            {"registration", "Registration"},
            {"register", "Register"},
            {"tickets", "Tickets"},
            {"updateTheCell", "Update selection cell"},
            {"sort", "Sort"},
            {"filter", "Filter"},
            {"showAllTickets", "Show all tickets"},
            //Commands
            {"add", "Add"},
            {"addIfMax", "Add if maximal"},
            {"addIfMin", "Add if minimal"},
            {"maxByComment", "Maximal by comment"},
            {"printUniquePrices", "Print unique price"},
            {"help", "Help"},
            {"removeById", "Remove by id"},
            {"clear", "Clear"},
            {"info", "Info"},
            {"sumOfDiscount", "Sum of discount"},
            {"removeGreater", "Remove greater"},
            {"executeScript", "Execute script"},
            {"update", "Update"},
            {"visualize", "Visualize"},
            //server answers
            {"Ticket added", "Ticket added"},
            {"Element isn't maximal", "Element isn't maximal"},
            {"Element isn't minimal", "Element isn't minimal"},
            {"Your tickets deleted", "Your tickets deleted"},
            {"Collection is empty", "Collection is empty"},
            {"User sign up", "User sign up"},
            {"Ticket with this id not found", "Ticket with this id not found"},
            {"It's not your ticket", "It's not your ticket"},
            {"Ticket deleted", "Ticket deleted"},
            {"Incorrect id", "Incorrect id"},
            {"Ticket updated", "Ticket updated"},
            {"You haven't rights to change this ticket", "You haven't rights to change this ticket"},
            {"You authorization", "You authorization"},
            {"User not found", "User not found"},
            {"Your data is too long", "Your data is too long"},
            {"User with this login already exists", "User with this login already exists"},
            {"Database crashed, exit", "Database crashed, exit"},

            {"Script executed", "Script executed"},
            {"Problems with the file which you enter", "Problems with the file which you enter"},
            {"Error! Recursive in script", "Error! Recursive in script"},
            {"Incorrect id", "Incorrect id"},
            {"Server is tired. Try to reconnect later", "Server is tired. Try to reconnect later"},
            {"Price: Price must be more then 0", "Price: Price must be more then 0"},
            {"Name: Incorrect name", "Name: Incorrect name"},
            {"X: Please enter a double argument", "X: Please enter a double argument"},
            {"Y: Please enter a float argument", "Y: Please enter a float argument"},
            {"Y: y-coordinate must be less than " + MAX_Y, "Y: y-coordinate must be less than " + MAX_Y},
            {"Discount: Discount can be from 0 to 100", "Discount: Discount can be from 0 to 100"},
            {"Comment: Incorrect comment", "Comment: Incorrect comment"},
            {"Price: Please enter a float argument", "Price: Please enter a float argument"},
            {"Discount: Please enter a long argument", "Discount: Please enter a long argument"},
            {"Minimal Age: Please enter an integer argument", "Minimal Age: Please enter an integer argument"},
            {"Tickets Count: Please enter a long argument", "Tickets Count: Please enter a long argument"},
            {"Tickets Count: Tickets count must be more than 0", "Tickets Count: Tickets count must be more than 0"},
            {"Discount: Please enter a long argument", "Discount: Please enter a long argument"},
            {"Event Name: Incorrect name", "Event Name: Incorrect name"},
            {"Login can't be empty", "Login can't be empty"},
            {"Please select field", "Please select field"}
    };

    @Override
    protected Object[][] getContents() {
        return content;
    }
}
