package riders.bank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditCreatedDTO {
    boolean isRecommended;
    String creditId;
}
