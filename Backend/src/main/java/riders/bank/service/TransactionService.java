package riders.bank.service;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;
import riders.bank.dto.SuspiciousTransactionDTO;
import riders.bank.dto.TransactionBodyDTO;
import riders.bank.exception.*;
import riders.bank.model.*;
import riders.bank.repository.TransactionRepository;
import riders.bank.utils.LocationUtils;
import riders.bank.utils.helpers.TransactionsHelper;

import java.io.IOException;
import java.io.ObjectInputFilter;
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
    private final UserService userService;
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
        TransactionsHelper transactionHelper = new TransactionsHelper();
        kieSession.insert(transactionHelper);
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
            specificTransaction.setStatus(Transaction.Status.SUSPICIOUS);
            transactionRepository.save(specificTransaction);
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

    public ArrayList<SuspiciousTransactionDTO> getAllSuspiciousForClient(String emailFromRequest) throws IOException, GeoIp2Exception {
        Client client = (Client) userService.getUserBy(emailFromRequest);
        ArrayList<BankAccount> clientBankAccounts = bankAccountService.getByClient(client);
        if(clientBankAccounts.isEmpty())
            return new ArrayList<>();
        ArrayList<Transaction> transactionsToReturn = new ArrayList<>();
        for (BankAccount account:clientBankAccounts) {
            transactionsToReturn.addAll(transactionRepository.findAllByBankAccountAndStatus(account, Transaction.Status.SUSPICIOUS));
        }
        ArrayList<SuspiciousTransaction> suspiciousTransactions = new ArrayList<>();
        for(Transaction transaction : transactionsToReturn){
            suspiciousTransactions.addAll(suspiciousTransactionService.getByTransaction(transaction));
        }
        ArrayList<SuspiciousTransactionDTO> suspiciousTransactionDTOs = new ArrayList<>();
        for(SuspiciousTransaction transaction : suspiciousTransactions){
            suspiciousTransactionDTOs.add(createSuspiciousTransactionDTOFromObject(transaction));
        }
        return suspiciousTransactionDTOs;

    }

    private SuspiciousTransactionDTO createSuspiciousTransactionDTOFromObject(SuspiciousTransaction transaction) throws IOException, GeoIp2Exception {
        SuspiciousTransactionDTO dto = new SuspiciousTransactionDTO();
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getTransaction().getAmount());
        dto.setCreationTime(transaction.getTransaction().getCreationTime());
        dto.setLocation(LocationUtils.getCityAndCountryName(transaction.getTransaction().getLocation()));
        dto.setBankAccountNumber(transaction.getTransaction().getBankAccount().getNumber());
        dto.setMessage(transaction.getMessage());
        return dto;
    }

    public void acceptSuspiciousTransaction(String transactionId) throws SuspiciousTransactionMissingException, AmountInBankException {
        SuspiciousTransaction suspiciousTransaction = suspiciousTransactionService.getById(transactionId);
        Double amount = suspiciousTransaction.getTransaction().getBankAccount().getBalance() - suspiciousTransaction.getTransaction().getAmount();
        if(amount < 0)
            throw new AmountInBankException();
        BankAccount bankAccount = suspiciousTransaction.getTransaction().getBankAccount();
        bankAccount.setBalance(bankAccount.getBalance() - suspiciousTransaction.getTransaction().getAmount());
        bankAccountService.save(bankAccount);
        Transaction transaction = suspiciousTransaction.getTransaction();
        transaction.setStatus(Transaction.Status.ACCEPTED);
        transactionRepository.save(transaction);
        suspiciousTransactionService.delete(suspiciousTransaction);
    }

    public void cancelSuspiciousTransaction(String transactionId) throws SuspiciousTransactionMissingException {
        SuspiciousTransaction suspiciousTransaction = suspiciousTransactionService.getById(transactionId);
        Transaction transaction = suspiciousTransaction.getTransaction();
        transaction.setStatus(Transaction.Status.REJECTED);
        transactionRepository.save(transaction);
        suspiciousTransactionService.delete(suspiciousTransaction);
    }
}
