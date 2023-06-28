package riders.bank.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import riders.bank.exception.BankAccountNotHaveCardException;
import riders.bank.exception.SuspiciousTransactionMissingException;
import riders.bank.model.SuspiciousTransaction;
import riders.bank.model.Transaction;
import riders.bank.repository.SuspiciousTransactionRepository;
import riders.bank.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.UUID;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SuspiciousTransactionService {
    private final SuspiciousTransactionRepository suspiciousTransactionRepository;

    public SuspiciousTransaction Create(SuspiciousTransaction transaction) {
        return suspiciousTransactionRepository.save(transaction);
    }

    public ArrayList<SuspiciousTransaction> getByTransaction(Transaction transaction){
        return suspiciousTransactionRepository.findAllByTransaction(transaction);
    }

    public SuspiciousTransaction getById(String transactionId) throws SuspiciousTransactionMissingException {
        Optional<SuspiciousTransaction> transaction = suspiciousTransactionRepository.findById(UUID.fromString(transactionId));
        if(transaction.isEmpty())
            throw new SuspiciousTransactionMissingException();
        return transaction.get();
    }

    public void delete(SuspiciousTransaction suspiciousTransaction) {
        suspiciousTransactionRepository.delete(suspiciousTransaction);
    }
}
