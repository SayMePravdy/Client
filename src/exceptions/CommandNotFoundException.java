package exceptions;

public class CommandNotFoundException extends Exception {
    public CommandNotFoundException(String msg) {
        super(msg);
    }
}
