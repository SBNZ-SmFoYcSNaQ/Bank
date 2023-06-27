package riders.bank.exception;

public class SuspiciousTransactionException extends Exception{
    public SuspiciousTransactionException(){
        super("Please check your bank account. This looks like suspicious transaction.");
    }
}
