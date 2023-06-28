package riders.bank.exception;

public class AmountInBankException extends Exception {
    public AmountInBankException() {
        super("Don't have enough money in bank!");
    }
}
