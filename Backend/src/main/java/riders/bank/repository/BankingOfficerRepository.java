package riders.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import riders.bank.model.BankingOfficer;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankingOfficerRepository extends JpaRepository<BankingOfficer, UUID> {
    Optional<BankingOfficer> findByEmail(String email);
}
