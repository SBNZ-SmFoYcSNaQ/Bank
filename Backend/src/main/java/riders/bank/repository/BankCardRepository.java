package riders.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import riders.bank.model.BankCard;
import riders.bank.model.BankingOfficer;

import java.util.UUID;
import java.util.Optional;
import java.time.LocalDate;

@Repository
public interface BankCardRepository extends JpaRepository<BankCard, UUID> {
    Optional<BankCard> findByNumberAndOwnerNameAndCvvCvcAndExpirationDate(String number, String ownerName, String cvvCvc, LocalDate expirationDate);
}

