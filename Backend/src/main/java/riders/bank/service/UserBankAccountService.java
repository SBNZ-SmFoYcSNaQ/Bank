package riders.bank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import riders.bank.exception.UserNotFoundException;
import riders.bank.model.BankAccount;
import riders.bank.model.BankCard;
import riders.bank.model.Client;
import riders.bank.repository.BankAccountRepository;
import riders.bank.repository.BankCardRepository;
import riders.bank.repository.ClientRepository;
import riders.bank.utils.RandomStringGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserBankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final ClientRepository clientRepository;
    private final BankCardRepository bankCardRepository;

    private static final int bankAccountNumberLength = 19;
    private static final int bankCardNumberLength = 16;
    private static final int bankCardDurationInYear = 2;

    public BankAccount createBankAccount(String email) throws UserNotFoundException {
        Client client = clientRepository
                .findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        BankAccount bankAccount = createNewBankAccountInstance(client);

        bankAccount = bankAccountRepository.save(bankAccount);

        return bankAccount;
    }

    public List<BankAccount> getAllByUserEmail(String email) throws UserNotFoundException {
        Client client = clientRepository
                .findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return bankAccountRepository.findAllByClient(client);
    }

    private BankAccount createNewBankAccountInstance(Client client) {
        BankAccount bankAccount = new BankAccount();
        BankCard bankCard = createNewBankCardInstance(client);

        bankAccount.setId(UUID.randomUUID());
        bankAccount.setNumber(RandomStringGenerator.generateRandomString(bankAccountNumberLength));
        bankAccount.setBankCard(bankCard);
        bankAccount.setClient(client);

        return bankAccount;
    }

    private BankCard createNewBankCardInstance(Client client) {
        BankCard bankCard = new BankCard();
        Random random  = new Random(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));

        bankCard.setId(UUID.randomUUID());
        bankCard.setNumber(RandomStringGenerator.generateRandomString(bankCardNumberLength));
        bankCard.setCvvCvc(String.valueOf(random.nextInt(10001)));
        bankCard.setOwnerName(String.format("%s %s", client.getFirstname(), client.getLastname()));
        bankCard.setExpirationDate(LocalDate.now().plusYears(bankCardDurationInYear));

        bankCard = bankCardRepository.save(bankCard);

        return bankCard;
    }
}
