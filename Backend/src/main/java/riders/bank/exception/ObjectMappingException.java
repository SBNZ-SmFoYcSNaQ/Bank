package riders.bank.exception;

public class ObjectMappingException extends Exception {
    public ObjectMappingException(String message) {
        super("Failed to map data to" + message + "object");
    }
}
