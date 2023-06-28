package riders.bank.exception;

public class BankCardNotExistingExceptions extends Exception {
    public BankCardNotExistingExceptions() {
        super("Bank card don't exist!");
    }
}
