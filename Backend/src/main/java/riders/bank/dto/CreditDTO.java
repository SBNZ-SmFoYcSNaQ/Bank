package riders.bank.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreditDTO {
    private double amount;
    private int paymentsNumber;
    private LocalDate minRepaymentPeriod;
    private LocalDate maxRepaymentPeriod;
    private String userId;
    private LocalDate employmentStartDate;
    private LocalDate employmentEndDate;
    private double salary;
}
