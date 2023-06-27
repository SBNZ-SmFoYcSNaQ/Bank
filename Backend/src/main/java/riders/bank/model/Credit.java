package riders.bank.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Credit {
    public enum Status {
        PENDING,
        IN_PROGRESS,
        PAID_OFF,
        REJECTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    private double amount;
    private int paymentsNumber;
    private LocalDateTime minimumRepaymentPeriod;
    private LocalDateTime maximumRepaymentPeriod;
    private Status status;
}
