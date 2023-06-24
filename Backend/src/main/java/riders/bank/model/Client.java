package riders.bank.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Entity
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue("User")
public class Client extends User {
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "client")
    private ArrayList<BankAccount> bankAccounts;
}
