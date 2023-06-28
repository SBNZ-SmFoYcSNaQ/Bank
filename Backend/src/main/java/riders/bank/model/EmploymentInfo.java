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
public class EmploymentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    private LocalDate employmentStartDate;  // if null then unemployed
    private LocalDate employmentEndDate; // if null then indefinite
    private double salary;

    public EmploymentInfo(LocalDate employmentStartDate, LocalDate employmentEndDate, double salary) {
        this.employmentStartDate = employmentStartDate;
        this.employmentEndDate = employmentEndDate;
        this.salary = salary;
    }
}
