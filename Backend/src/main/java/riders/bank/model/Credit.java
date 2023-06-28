package riders.bank.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    private LocalDate minimumRepaymentPeriod;
    private LocalDate maximumRepaymentPeriod;
    private Status status;
    @OneToOne(cascade = CascadeType.REMOVE)
    private EmploymentInfo employmentInfo;
    @ManyToOne
    private Client client;
    
    @Transient
    private boolean isRecommended;

    public Credit(double amount, int paymentsNumber, LocalDate minimumRepaymentPeriod, LocalDate maximumRepaymentPeriod, Status status, EmploymentInfo employmentInfo, Client client) {
        this.amount = amount;
        this.paymentsNumber = paymentsNumber;
        this.minimumRepaymentPeriod = minimumRepaymentPeriod;
        this.maximumRepaymentPeriod = maximumRepaymentPeriod;
        this.status = status;
        this.employmentInfo = employmentInfo;
        this.client = client;
    }
}
