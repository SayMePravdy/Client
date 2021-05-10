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
                coms.add(new Data(command, args, Client.getLogin(), Client.getPassword()));
                break;

            case "register":
            case "authorization":
                args.add(processor.getLogin());
                args.add(processor.getPassword());
                coms.add(new Data(command, args, (String) args.get(0), (String) args.get(1)));
                break;


            case "add":
            case "add_if_max":
            case "add_if_min":
            case "remove_greater":
                args.add(processor.getTicket());
                coms.add(new Data(command, args, Client.getLogin(), Client.getPassword()));
                break;

            case "update":
                args.add(processor.getId());
                args.add(processor.getTicket());
                coms.add(new Data(command, args, Client.getLogin(), Client.getPassword()));
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
        return coms;
    }
}
