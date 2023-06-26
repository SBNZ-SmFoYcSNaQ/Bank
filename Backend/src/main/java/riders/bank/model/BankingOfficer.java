package riders.bank.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("banking_officer")
public class BankingOfficer extends User {
}
