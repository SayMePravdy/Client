package client;

import data.Data;
import exceptions.CommandNotFoundException;
import exceptions.RecursiveScript;
import processor.FileProcessor;
import processor.Processor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private String command;
    private Processor processor;

    public CommandManager(String command, Processor processor) {
        this.command = command;
        this.processor = processor;
    }

    public List<Data> getCommands() throws CommandNotFoundException, IOException {
        List<Data> coms = new ArrayList<>();
        List<Object> args = new ArrayList<>();
        switch (command) {
            case "help":
            case "info":
            case "show":
            case "clear":
            case "sum_of_discount":
            case "max_by_comment":
            case "print_unique_price":
            case "exit":
            case "register":
                break;

            case "add":
            case "add_if_max":
            case "add_if_min":
            case "remove_greater":
                args.add(processor.getTicket());
                break;

            case "update":
                args.add(processor.getId());
                args.add(processor.getTicket());
                break;
            case "remove_by_id":
                args.add(processor.getId());
                break;
            case "execute_script":
                try {
                    FileProcessor fileProcessor = new FileProcessor(processor.getName(), processor.getHistory());
                    coms.addAll(fileProcessor.readData());
                } catch (RecursiveScript e) {
                    System.out.println(e.getMessage());
                }
                break;
            default:
                throw new CommandNotFoundException("Command \"" + command + "\" doesn't exist");
        }
        String login = processor.getLogin();
        String password = processor.getPassword();

        if (command.equals("register")) {
            args.add(login);
            args.add(password);
        }

        coms.add(new Data(command, args, login, password));
        return coms;
    }
}
