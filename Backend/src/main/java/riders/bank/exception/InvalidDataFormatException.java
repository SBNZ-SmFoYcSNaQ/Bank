package riders.bank.exception;

public class InvalidDataFormatException extends Exception {
    public InvalidDataFormatException() {
        super("Invalid data format, please provide valid data!");
    }
}
