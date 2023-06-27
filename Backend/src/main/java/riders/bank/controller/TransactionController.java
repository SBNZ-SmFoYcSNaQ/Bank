package riders.bank.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import riders.bank.App;
import riders.bank.dto.TransactionBodyDTO;
import riders.bank.exception.AmountInBankException;
import riders.bank.exception.BankAccountNotHaveCardException;
import riders.bank.exception.BankCardNotExistingExceptions;
import riders.bank.exception.SuspiciousTransactionException;
import riders.bank.service.TransactionService;
import riders.bank.utils.LocationUtils;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping()
    public ResponseEntity<?> MakeTransaction(HttpServletRequest request, @RequestBody TransactionBodyDTO transactionBodyDTO) {
        Map<String, String> responseObject = new HashMap<>();
        try {
            String ipAddress = request.getHeader("X-Forwarded-For");
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            }
            transactionService.makeTransaction(transactionBodyDTO, ipAddress);
            return new ResponseEntity<>(CREATED);
        } catch (BankCardNotExistingExceptions e) {
            responseObject.put("error", "Bank card don't exist!");
            return new ResponseEntity<>(responseObject, BAD_REQUEST);
        } catch (BankAccountNotHaveCardException e) {
            responseObject.put("error", "There is no bank account with provided card!");
            return new ResponseEntity<>(responseObject, BAD_REQUEST);
        } catch (AmountInBankException e) {
            responseObject.put("error", "There is no enough money in bank account!");
            return new ResponseEntity<>(responseObject, BAD_REQUEST);
        } catch (SuspiciousTransactionException e) {
            responseObject.put("error", "This looks like suspicious transaction. Please check your bank account!");
            return new ResponseEntity<>(responseObject, BAD_REQUEST);
        }
        catch (Exception e) {
            App.LOGGER.error(e.getMessage());
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }
}
