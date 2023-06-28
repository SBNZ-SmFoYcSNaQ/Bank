package riders.bank.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import riders.bank.model.BankAccount;
import riders.bank.model.BankCard;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBankAccountResponse {
    private UUID id;
    private String number;
    private double balance;
    private BankCard bankCard;

    public CreateBankAccountResponse(BankAccount bankAccount) {
        this.id = bankAccount.getId();
        this.number = bankAccount.getNumber();
        this.balance = bankAccount.getBalance();
        this.bankCard = bankAccount.getBankCard();
    }
}
