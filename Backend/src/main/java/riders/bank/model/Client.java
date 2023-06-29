package riders.bank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private List<BankAccount> bankAccounts;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "client")
    @JsonManagedReference
    private List<Credit> credits;
}
