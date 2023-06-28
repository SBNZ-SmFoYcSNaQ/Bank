package riders.bank.exception;

public class SuspiciousTransactionMissingException extends Exception{
    public SuspiciousTransactionMissingException(){
        super("Suspicious transaction is missing!");
    }
}
