package riders.bank.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import riders.bank.exception.BankCardNotExistingExceptions;
import riders.bank.model.BankCard;
import riders.bank.repository.BankCardRepository;
import riders.bank.repository.BankingOfficerRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BankCardService {
    private final BankCardRepository bankCardRepository;

    public BankCard getBankCardByFields(String number, String ownerName, String cvvCvc, LocalDate expirationDate) throws BankCardNotExistingExceptions {
        Optional<BankCard> bankCardOptional = bankCardRepository.findByNumberAndOwnerNameAndCvvCvcAndExpirationDate(number,ownerName,cvvCvc,expirationDate);
        if(bankCardOptional.isEmpty())
            throw new BankCardNotExistingExceptions();
        return bankCardOptional.get();
    }
}
