package riders.bank.dto;

import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import riders.bank.model.Transaction;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class SuspiciousTransactionDTO {
    private UUID id;
    private double amount;
    private LocalDateTime creationTime;
    private String location;
    private String bankAccountNumber;
    private String message;
}
