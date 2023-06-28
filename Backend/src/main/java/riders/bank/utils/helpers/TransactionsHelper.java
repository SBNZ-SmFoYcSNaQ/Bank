package riders.bank.utils.helpers;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import lombok.NoArgsConstructor;
import riders.bank.model.BankAccount;
import riders.bank.model.Transaction;
import riders.bank.utils.LocationUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.DAYS;

@NoArgsConstructor
public class TransactionsHelper {

    private static final double smallTransactionAmount = 1500;
    private static final double bigTransactionAmount = 10000;
    public List<Transaction> getTimeCloseTransactions(List<Transaction> otherTransactions, Transaction specificTransaction){
        List<Transaction> transactionsToReturn = new ArrayList<>();
        for(Transaction transaction : otherTransactions){
            if(isCloseTimeTransactions(transaction, specificTransaction)){
                transactionsToReturn.add(transaction);
            }
        }
        return transactionsToReturn;
    }

    public List<Transaction> getLongDistanceTransactions(List<Transaction> otherTransactions, Transaction specificTransaction) throws IOException, GeoIp2Exception {
        List<Transaction> transactionsToReturn = new ArrayList<>();
        for(Transaction transaction : otherTransactions){
            if(!LocationUtils.isSameCountry(transaction.getLocation(), specificTransaction.getLocation())){
                transactionsToReturn.add(transaction);
            }
        }
        return transactionsToReturn;
    }

    public List<Transaction> getClientTransactions(List<Transaction> otherTransactions, UUID clienId){
        List<Transaction> transactionsToReturn = new ArrayList<>();
        for (Transaction transaction: otherTransactions) {
            if(transaction.getBankAccount().getClient().getId().equals(clienId))
                transactionsToReturn.add(transaction);
        }
        return transactionsToReturn;
    }

    public List<Transaction> getSmallTransactions(List<Transaction> transactions) {
        List<Transaction> transactionsToReturn = new ArrayList<>();
        for (Transaction transaction: transactions) {
            if(transaction.getAmount() < smallTransactionAmount)
                transactionsToReturn.add(transaction);
        }
        return transactionsToReturn;
    }

    public Boolean isTransactionFromNewLocations(List<Transaction> transactions, String currentLocation) {
        for (Transaction transaction: transactions) {
            if(transaction.getLocation().equals(currentLocation))
                return false;
        }
        return true;
    }

    public Boolean isTransactionOdd(Transaction transaction) {
        return transaction.getAmount() > bigTransactionAmount && isOddTime(transaction.getCreationTime());
    }

    public Double getMeanTransactionAmount(List<Transaction> transactions) {
        double meanValue = 0.0;
        for (Transaction transaction:transactions) {
            meanValue += transaction.getAmount();
        }
        return meanValue/transactions.size();
    }

    private Boolean isOddTime(LocalDateTime time) {
        LocalTime localTime = time.toLocalTime();
        int hour = localTime.getHour();
        return hour >= 1 && hour <= 5;
    }

    private boolean isCloseTimeTransactions(Transaction transactionOne, Transaction transactionTwo) {
        return DAYS.between(transactionOne.getCreationTime(), transactionTwo.getCreationTime()) <= 1;
    }
}
