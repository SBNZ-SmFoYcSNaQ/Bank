package riders.bank.Transactions;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.boot.test.context.SpringBootTest;
import riders.bank.model.BankAccount;
import riders.bank.model.BankCard;
import riders.bank.model.Client;
import riders.bank.model.Transaction;
import riders.bank.utils.helpers.TransactionsHelper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransactionsTests {
    private KieSession kieSession;
    private static final String transactionsDrlFile = "rules/transactions/transactions-rules.drl";

    private Client client;
    private BankAccount bankAccount;
    private Transaction specificTransaction;
    private Transaction otherTransaction;
    private List<String> suspiciousMessages = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        KieServices kieServices = KieServices.Factory.get();

        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource(transactionsDrlFile));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();

        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
        kieSession = kieContainer.newKieSession();

        client = createClient();
        bankAccount = createBankAccount(client);
    }

    @Test
    public void whenTransactionIsFromOldLocationInCloseTime_TransactionNotSuspicious() {
        specificTransaction = createTransactionWithLocationAndDate("212.200.65.93", LocalDateTime.now());
        otherTransaction = createTransactionWithLocationAndDate("212.200.65.93", LocalDateTime.now());
        setUpGlobalVariables();
        insertFactsIntoSession(specificTransaction, otherTransaction);

        fireRules("ruleOne");

        assertFalse(suspiciousMessages.contains("Many transactions from different locations in close time."));
    }

    @Test
    public void whenTransactionIsFromOldLocationNotInCloseTime_TransactionNotSuspicious() {
        specificTransaction = createTransactionWithLocationAndDate("212.200.65.93", LocalDateTime.now());
        otherTransaction = createTransactionWithLocationAndDate("212.200.65.93", LocalDateTime.now().minusYears(2));
        setUpGlobalVariables();
        insertFactsIntoSession(specificTransaction, otherTransaction);

        fireRules("ruleOne");

        assertFalse(suspiciousMessages.contains("Many transactions from different locations in close time."));
    }


    @Test
    public void whenTransactionIsFromNewLocationInCloseTime_TransactionIsSuspicious() {
        specificTransaction = createTransactionWithLocationAndDate("93.87.60.180", LocalDateTime.now());
        otherTransaction = createTransactionWithLocationAndDate("23.19.74.164", LocalDateTime.now());
        setUpGlobalVariables();
        insertFactsIntoSession(specificTransaction, otherTransaction);

        fireRules("ruleOne");

        assertTrue(suspiciousMessages.contains("Many transactions from different locations in close time."));
    }

    @Test
    public void whenTransactionIsFromNewLocationNotInCloseTime_TransactionNotSuspicious() {
        specificTransaction = createTransactionWithLocationAndDate("93.87.60.180", LocalDateTime.now());
        otherTransaction = createTransactionWithLocationAndDate("23.19.74.164", LocalDateTime.now().minusYears(2));
        setUpGlobalVariables();
        insertFactsIntoSession(specificTransaction, otherTransaction);

        fireRules("ruleOne");

        assertFalse(suspiciousMessages.contains("Many transactions from different locations in close time."));
    }

    @Test
    public void whenTransactionIsSmallAmountInCloseTime_TransactionIsSuspicious() {
        specificTransaction = createTransactionWithAmountAndDate(500.0, LocalDateTime.now());
        otherTransaction = createTransactionWithAmountAndDate(500.0, LocalDateTime.now());
        Transaction otherTransactionTwo = createTransactionWithAmountAndDate(500.0, LocalDateTime.now());
        Transaction otherTransactionThree = createTransactionWithAmountAndDate(500.0, LocalDateTime.now());
        Transaction otherTransactionFour = createTransactionWithAmountAndDate(500.0, LocalDateTime.now());
        Transaction otherTransactionFive = createTransactionWithAmountAndDate(500.0, LocalDateTime.now());
        Transaction otherTransactionSix = createTransactionWithAmountAndDate(500.0, LocalDateTime.now());
        setUpGlobalVariables();
        insertFactsIntoSession(specificTransaction, otherTransaction, otherTransactionTwo, otherTransactionThree, otherTransactionFour, otherTransactionFive, otherTransactionSix);

        fireRules("ruleTwo");

        assertTrue(suspiciousMessages.contains("Many small transactions in close time."));
    }

    @Test
    public void whenTransactionIsSmallAmountNotInCloseTime_TransactionNotSuspicious(){
        specificTransaction = createTransactionWithAmountAndDate(500.0, LocalDateTime.now());
        otherTransaction = createTransactionWithAmountAndDate(500.0, LocalDateTime.now().minusYears(2));
        Transaction otherTransactionTwo = createTransactionWithAmountAndDate(500.0, LocalDateTime.now());
        Transaction otherTransactionThree = createTransactionWithAmountAndDate(500.0, LocalDateTime.now().minusYears(2));
        Transaction otherTransactionFour = createTransactionWithAmountAndDate(500.0, LocalDateTime.now());
        Transaction otherTransactionFive = createTransactionWithAmountAndDate(500.0, LocalDateTime.now().minusYears(2));
        Transaction otherTransactionSix = createTransactionWithAmountAndDate(500.0, LocalDateTime.now());
        setUpGlobalVariables();
        insertFactsIntoSession(specificTransaction, otherTransaction, otherTransactionTwo, otherTransactionThree, otherTransactionFour, otherTransactionFive, otherTransactionSix);

        fireRules("ruleTwo");

        assertFalse(suspiciousMessages.contains("Many small transactions in close time."));
    }


    @Test
    public void whenTransactionIsBigAmountInCloseTime_TransactionNotSuspicious() {
        specificTransaction = createTransactionWithAmountAndDate(20000.0, LocalDateTime.now());
        otherTransaction = createTransactionWithAmountAndDate(20000.0, LocalDateTime.now());
        Transaction otherTransactionTwo = createTransactionWithAmountAndDate(20000.0, LocalDateTime.now());
        Transaction otherTransactionThree = createTransactionWithAmountAndDate(20000.0, LocalDateTime.now());
        Transaction otherTransactionFour = createTransactionWithAmountAndDate(20000.0, LocalDateTime.now());
        Transaction otherTransactionFive = createTransactionWithAmountAndDate(20000.0, LocalDateTime.now());
        Transaction otherTransactionSix = createTransactionWithAmountAndDate(20000.0, LocalDateTime.now());
        setUpGlobalVariables();
        insertFactsIntoSession(specificTransaction, otherTransaction, otherTransactionTwo, otherTransactionThree, otherTransactionFour, otherTransactionFive, otherTransactionSix);

        fireRules("ruleTwo");

        assertFalse(suspiciousMessages.contains("Many small transactions in close time."));
    }

    @Test
    public void whenTransactionIsBigAmountNotInCloseTime_TransactionNotSuspicious() {
        specificTransaction = createTransactionWithAmountAndDate(20000.0, LocalDateTime.now());
        otherTransaction = createTransactionWithAmountAndDate(20000.0, LocalDateTime.now().minusYears(2));
        Transaction otherTransactionTwo = createTransactionWithAmountAndDate(20000.0, LocalDateTime.now());
        Transaction otherTransactionThree = createTransactionWithAmountAndDate(20000.0, LocalDateTime.now().minusYears(2));
        Transaction otherTransactionFour = createTransactionWithAmountAndDate(20000.0, LocalDateTime.now());
        Transaction otherTransactionFive = createTransactionWithAmountAndDate(20000.0, LocalDateTime.now().minusYears(2));
        Transaction otherTransactionSix = createTransactionWithAmountAndDate(20000.0, LocalDateTime.now());
        setUpGlobalVariables();
        insertFactsIntoSession(specificTransaction, otherTransaction, otherTransactionTwo, otherTransactionThree, otherTransactionFour, otherTransactionFive, otherTransactionSix);

        fireRules("ruleTwo");

        assertFalse(suspiciousMessages.contains("Many small transactions in close time."));
    }

    @Test
    public void whenTransactionIsFromNewLocation_TransactionIsSuspicious() {
        specificTransaction = createTransactionWithLocationAndDate("93.87.60.180", LocalDateTime.now());
        otherTransaction = createTransactionWithLocationAndDate("23.19.74.164", LocalDateTime.now());
        setUpGlobalVariables();
        insertFactsIntoSession(specificTransaction, otherTransaction);

        fireRules("ruleThree");

        assertTrue(suspiciousMessages.contains("Transaction from new location."));
    }

    @Test
    public void whenTransactionIsFromOldLocation_TransactionNotSuspicious() {
        specificTransaction = createTransactionWithLocationAndDate("23.19.74.164", LocalDateTime.now());
        otherTransaction = createTransactionWithLocationAndDate("23.19.74.164", LocalDateTime.now());
        setUpGlobalVariables();
        insertFactsIntoSession(specificTransaction, otherTransaction);

        fireRules("ruleThree");

        assertFalse(suspiciousMessages.contains("Transaction from new location."));
    }

    @Test
    public void whenTransactionIsBigAmountInOddTime_TransactionIsSuspicious() {
        specificTransaction = createTransactionWithAmountAndDate(50000.0, LocalDateTime.now());
        otherTransaction = createTransactionWithAmountAndDate(50000.0, LocalDateTime.now());
        setUpGlobalVariables();
        insertFactsIntoSessionWithMockedHelperForOddTransaction(specificTransaction, otherTransaction);

        fireRules("ruleFour");

        assertTrue(suspiciousMessages.contains("Transaction is big and in odd time."));
    }

    @Test
    public void whenTransactionIsBigAmountNotInOddTime_TransactionIsSuspicious() {
        specificTransaction = createTransactionWithAmountAndDate(50000.0, LocalDateTime.now());
        otherTransaction = createTransactionWithAmountAndDate(50000.0, LocalDateTime.now());
        setUpGlobalVariables();
        insertFactsIntoSessionWithMockedHelperForNormalTransaction(specificTransaction, otherTransaction);

        fireRules("ruleFour");

        assertFalse(suspiciousMessages.contains("Transaction is big and in odd time."));
    }

    @Test
    public void whenTransactionIsNotBigAmountInOddTime_TransactionIsSuspicious() {
        specificTransaction = createTransactionWithAmountAndDate(500.0, LocalDateTime.now());
        otherTransaction = createTransactionWithAmountAndDate(500.0, LocalDateTime.now());
        setUpGlobalVariables();
        insertFactsIntoSessionWithMockedHelperForNormalTransaction(specificTransaction, otherTransaction);

        fireRules("ruleFour");

        assertFalse(suspiciousMessages.contains("Transaction is big and in odd time."));
    }

    @Test
    public void whenTransactionIsNotBigAmountNotInOddTime_TransactionIsSuspicious() {
        specificTransaction = createTransactionWithAmountAndDate(500.0, LocalDateTime.now());
        otherTransaction = createTransactionWithAmountAndDate(500.0, LocalDateTime.now());
        setUpGlobalVariables();
        insertFactsIntoSessionWithMockedHelperForNormalTransaction(specificTransaction, otherTransaction);

        fireRules("ruleFour");

        assertFalse(suspiciousMessages.contains("Transaction is big and in odd time."));
    }

    @Test
    public void whenTransactionIsBiggerThanUsually_TransactionIsSuspicious() {
        specificTransaction = createTransactionWithAmountAndDate(20000.0, LocalDateTime.now());
        otherTransaction = createTransactionWithAmountAndDate(1000.0, LocalDateTime.now());
        Transaction otherTransactionTwo = createTransactionWithAmountAndDate(2000.0, LocalDateTime.now());
        Transaction otherTransactionThree = createTransactionWithAmountAndDate(3000.0, LocalDateTime.now());
        setUpGlobalVariables();
        insertFactsIntoSession(specificTransaction, otherTransaction, otherTransactionTwo, otherTransactionThree);

        fireRules("ruleFive");

        assertTrue(suspiciousMessages.contains("Transaction is much larger than average."));
    }

    @Test
    public void whenTransactionIsNotBiggerThanUsually_TransactionNotSuspicious() {
        specificTransaction = createTransactionWithAmountAndDate(2000.0, LocalDateTime.now());
        otherTransaction = createTransactionWithAmountAndDate(1000.0, LocalDateTime.now());
        Transaction otherTransactionTwo = createTransactionWithAmountAndDate(2000.0, LocalDateTime.now());
        Transaction otherTransactionThree = createTransactionWithAmountAndDate(3000.0, LocalDateTime.now());
        setUpGlobalVariables();
        insertFactsIntoSession(specificTransaction, otherTransaction, otherTransactionTwo, otherTransactionThree);

        fireRules("ruleFive");

        assertFalse(suspiciousMessages.contains("Transaction is much larger than average."));
    }



    private void fireRules(String agendaGroup){
        kieSession.getAgenda().getAgendaGroup(agendaGroup).setFocus();
        kieSession.fireAllRules();
        kieSession.dispose();
    }
    private void setUpGlobalVariables() {
        kieSession.setGlobal("specificTransaction", specificTransaction);
        kieSession.setGlobal("specificClient", client);
        kieSession.setGlobal("suspiciousMessages", suspiciousMessages);
    }

    private Client createClient() {
        Client client = new Client();
        client.setId(UUID.randomUUID());
        return client;
    }

    private BankAccount createBankAccount(Client client) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setClient(client);
        return bankAccount;
    }

    private Transaction createTransactionWithLocationAndDate(String location, LocalDateTime dateTime) {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setCreationTime(dateTime);
        transaction.setLocation(location);
        transaction.setBankAccount(bankAccount);
        return transaction;
    }

    private Transaction createTransactionWithAmountAndDate(Double amount, LocalDateTime dateTime) {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setCreationTime(dateTime);
        transaction.setAmount(amount);
        transaction.setLocation("93.87.60.180");
        transaction.setBankAccount(bankAccount);
        return transaction;
    }

    private void insertFactsIntoSession(Object... facts) {
        TransactionsHelper transactionsHelperToUse = new TransactionsHelper();
        for (Object fact : facts) {
            kieSession.insert(fact);
        }
        kieSession.insert(transactionsHelperToUse);
    }

    private void insertFactsIntoSessionWithMockedHelperForOddTransaction(Object... facts) {
        TransactionsHelper transactionsHelperMocked = mock(TransactionsHelper.class);
        when(transactionsHelperMocked.isTransactionOdd(any())).thenReturn(true);
        for (Object fact : facts) {
            kieSession.insert(fact);
        }
        kieSession.insert(transactionsHelperMocked);
    }

    private void insertFactsIntoSessionWithMockedHelperForNormalTransaction(Object... facts) {
        TransactionsHelper transactionsHelperMocked = mock(TransactionsHelper.class);
        when(transactionsHelperMocked.isTransactionOdd(any())).thenReturn(false);
        for (Object fact : facts) {
            kieSession.insert(fact);
        }
        kieSession.insert(transactionsHelperMocked);
    }

}
