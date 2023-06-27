package riders.bank.exception;

public class BankAccountNotHaveCardException extends Exception{
    public BankAccountNotHaveCardException() {
        super("Provided bank account don't have provided bank card");
    }
}
