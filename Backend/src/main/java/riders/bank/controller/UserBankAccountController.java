package riders.bank.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import riders.bank.dto.CreateBankAccountResponse;
import riders.bank.exception.UserNotFoundException;
import riders.bank.model.BankAccount;
import riders.bank.security.AuthUtility;
import riders.bank.service.UserBankAccountService;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserBankAccountController {

    private final UserBankAccountService userBankAccountService;

    @PostMapping("/users/me/bankaccounts/add")
    @Secured("Client")
    public ResponseEntity<CreateBankAccountResponse> CreateBankAccount(HttpServletRequest request) {
        BankAccount bankAccount;
        try {
            String email = AuthUtility.getEmailFromRequest(request);
            bankAccount = userBankAccountService.createBankAccount(email);
        } catch (UserNotFoundException ex) {
            System.out.println(ex.toString());
            return new ResponseEntity<>(BAD_REQUEST);
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new CreateBankAccountResponse(bankAccount), CREATED);
    }

    @GetMapping("/users/me/bankaccounts")
    @Secured("Client")
    public ResponseEntity<List<BankAccount>> GetBankAccounts(HttpServletRequest request) {
        List<BankAccount> bankAccounts;
        try {
            String email = AuthUtility.getEmailFromRequest(request);
            bankAccounts = userBankAccountService.getAllByUserEmail(email);
        } catch (UserNotFoundException ex) {
            System.out.println(ex.toString());
            return new ResponseEntity<>(BAD_REQUEST);
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(bankAccounts, OK);
    }
}
