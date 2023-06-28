package riders.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import riders.bank.model.BankAccount;
import riders.bank.model.SuspiciousTransaction;
import riders.bank.model.Transaction;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface SuspiciousTransactionRepository extends JpaRepository<SuspiciousTransaction, UUID> {
    ArrayList<SuspiciousTransaction> findAllByTransaction(Transaction transaction);
}
