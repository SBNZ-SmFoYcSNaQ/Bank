package riders.bank.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction {
    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED,
        SUSPICIOUS
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    private Status status;
    private double amount;
    private LocalDateTime creationTime;
    private LocalDateTime executionTime;
    @OneToOne(optional = false)
    private BankAccount bankAccount;
    private String location;
}
