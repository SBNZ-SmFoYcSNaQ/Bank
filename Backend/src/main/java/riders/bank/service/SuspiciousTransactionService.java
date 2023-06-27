package riders.bank.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import riders.bank.model.SuspiciousTransaction;
import riders.bank.repository.SuspiciousTransactionRepository;
import riders.bank.repository.TransactionRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class SuspiciousTransactionService {
    private final SuspiciousTransactionRepository suspiciousTransactionRepository;

    public SuspiciousTransaction Create(SuspiciousTransaction transaction) {
        return suspiciousTransactionRepository.save(transaction);
    }
}
