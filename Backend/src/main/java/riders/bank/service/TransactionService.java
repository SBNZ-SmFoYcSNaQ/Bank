package riders.bank.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;
import riders.bank.dto.TransactionBodyDTO;
import riders.bank.exception.AmountInBankException;
import riders.bank.exception.BankAccountNotHaveCardException;
import riders.bank.exception.BankCardNotExistingExceptions;
import riders.bank.exception.SuspiciousTransactionException;
import riders.bank.model.BankAccount;
import riders.bank.model.BankCard;
import riders.bank.model.SuspiciousTransaction;
import riders.bank.model.Transaction;
import riders.bank.repository.TransactionRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final BankCardService bankCardService;
    private final BankAccountService bankAccountService;
    private final SuspiciousTransactionService suspiciousTransactionService;
    private final KieContainer kieContainer;
    public void makeTransaction(TransactionBodyDTO transactionBodyDTO, String clientLocation) throws BankCardNotExistingExceptions, BankAccountNotHaveCardException, AmountInBankException, SuspiciousTransactionException {
        BankCard bankCard = checkBankCardDetails(transactionBodyDTO);
        BankAccount bankAccount = checkBankAccountDetails(bankCard);
        if(bankAccount.getBalance() < transactionBodyDTO.getAmount())
            throw new AmountInBankException();
        Transaction transaction = createTransactionFromDTO(transactionBodyDTO, clientLocation, bankAccount);
        Transaction specificTransaction = transactionRepository.save(transaction);
        List<String> suspiciousMessages = new ArrayList<>();
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.setGlobal("specificTransaction", specificTransaction);
        kieSession.setGlobal("specificClient", bankAccount.getClient());
        kieSession.setGlobal("suspiciousMessages", suspiciousMessages);
        List<Transaction> allTransactions = transactionRepository.findAll();
        List<BankAccount> allBankAccounts = bankAccountService.getAll();
        for (Transaction transactionIter : allTransactions) {
            kieSession.insert(transactionIter);
        }
        for(BankAccount bankAccountIter : allBankAccounts) {
            kieSession.insert(bankAccountIter);
        }
        kieSession.fireAllRules();

        if(suspiciousMessages.size() > 0) {
            String messageToAdd = "";
            for (String message : suspiciousMessages) {
                messageToAdd += message;
            }
            SuspiciousTransaction suspiciousTransaction = new SuspiciousTransaction();
            suspiciousTransaction.setMessage(messageToAdd);
            suspiciousTransaction.setTransaction(specificTransaction);
            suspiciousTransactionService.Create(suspiciousTransaction);
            throw new SuspiciousTransactionException();
        }
    }

    private Transaction createTransactionFromDTO(TransactionBodyDTO transactionBodyDTO, String location, BankAccount bankAccount) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionBodyDTO.getAmount());
        transaction.setCreationTime(LocalDateTime.now());
        transaction.setLocation(location);
        transaction.setExecutionTime(null);
        transaction.setStatus(Transaction.Status.PENDING);
        transaction.setBankAccount(bankAccount);
        return transaction;

    }
    private BankCard checkBankCardDetails(TransactionBodyDTO transactionBodyDTO) throws BankCardNotExistingExceptions {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate expirationDate = LocalDate.parse(transactionBodyDTO.getExpirationDate(), formatter);
        return bankCardService.getBankCardByFields(transactionBodyDTO.getCreditCardNumber(), transactionBodyDTO.getOwnerName(),
                transactionBodyDTO.getCvv_cvc(), expirationDate);
    }

    private BankAccount checkBankAccountDetails(BankCard bankCard) throws BankAccountNotHaveCardException {
        return bankAccountService.getByBankCard(bankCard);
    }
}
