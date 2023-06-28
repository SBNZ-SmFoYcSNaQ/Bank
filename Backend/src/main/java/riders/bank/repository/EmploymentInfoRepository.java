package riders.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import riders.bank.model.EmploymentInfo;

import java.util.UUID;

@Repository
public interface EmploymentInfoRepository extends JpaRepository<EmploymentInfo, UUID> {
}
