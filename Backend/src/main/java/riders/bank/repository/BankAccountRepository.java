package riders.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import riders.bank.model.BankAccount;
import riders.bank.model.BankCard;
import riders.bank.model.Client;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
    Optional<BankAccount> findByBankCard(BankCard bankCard);
    ArrayList<BankAccount> findAllByClient(Client client);
}
