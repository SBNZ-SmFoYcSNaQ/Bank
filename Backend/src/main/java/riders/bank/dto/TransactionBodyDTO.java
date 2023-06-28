package riders.bank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionBodyDTO {
    private String accountNumber;
    private Double amount;
    private String creditCardNumber;
    private String cvv_cvc;
    private String expirationDate;
    private String ownerName;
}
