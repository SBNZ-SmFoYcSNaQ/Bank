package riders.bank.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import riders.bank.model.enums.Role;
import riders.bank.model.enums.Sex;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "user_")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DiscriminatorColumn(name = "user_type", columnDefinition = "VARCHAR(32)")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
    private String address;
    private String phone;
    private Sex sex;
    private LocalDate birthdate;

    public String getRoleAsString() {
        return role == Role.CLIENT ? "CLIENT" : "BANKING_OFFICER";
    }
}
