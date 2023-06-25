package riders.bank.exception;

public class EmailExistsException extends Exception {
    public EmailExistsException() {
        super("Email address is already taken");
    }
}
