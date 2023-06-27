package riders.bank.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import riders.bank.exception.BankAccountNotHaveCardException;
import riders.bank.model.BankAccount;
import riders.bank.model.BankCard;
import riders.bank.repository.BankAccountRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    public BankAccount getByBankCard(BankCard bankCard) throws BankAccountNotHaveCardException
    {
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findByBankCard(bankCard);
        if(optionalBankAccount.isEmpty())
            throw new BankAccountNotHaveCardException();
        return optionalBankAccount.get();
    }

    public List<BankAccount> getAll() {
        return bankAccountRepository.findAll();
    }
}
