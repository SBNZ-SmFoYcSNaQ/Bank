package riders.bank.dto;

import lombok.Getter;
import lombok.Setter;
import riders.bank.model.enums.Sex;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterBodyDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String address;
    private String phone;
    private Sex sex;
    private LocalDate birthdate;
}
