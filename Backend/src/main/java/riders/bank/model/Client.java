package riders.bank.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue("client")
public class Client extends User {
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "client")
    private List<BankAccount> bankAccounts;
}
