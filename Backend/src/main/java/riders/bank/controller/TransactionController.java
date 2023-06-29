package riders.bank.controller;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import riders.bank.App;
import riders.bank.dto.SuspiciousTransactionDTO;
import riders.bank.dto.TransactionBodyDTO;
import riders.bank.exception.*;
import riders.bank.security.AuthUtility;
import riders.bank.service.TransactionService;

import java.io.IOException;
import java.util.ArrayList;
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

    @GetMapping("/suspicious")
    @Secured("CLIENT")
    public ResponseEntity<?> GetSuspicious(HttpServletRequest request) throws IOException, GeoIp2Exception {
        ArrayList<SuspiciousTransactionDTO> suspiciousTransactionsDTO = transactionService.getAllSuspiciousForClient(AuthUtility.getEmailFromRequest(request));
        return new ResponseEntity<>(suspiciousTransactionsDTO, OK);
    }

    @GetMapping("/suspicious/accept/{id}")
    @Secured("CLIENT")
    public ResponseEntity<?> AcceptSuspicious(@PathVariable("id") String transactionId) {
        Map<String, String> responseObject = new HashMap<>();
        try{
            transactionService.acceptSuspiciousTransaction(transactionId);
            return new ResponseEntity<>(OK);
        } catch(SuspiciousTransactionMissingException e){
            responseObject.put("error", "Suspicious transaction missing!");
            return new ResponseEntity<>(responseObject, BAD_REQUEST);
        } catch (AmountInBankException e) {
            responseObject.put("error", "You don't have enough money on balance to accept this transaction!");
            return new ResponseEntity<>(responseObject, BAD_REQUEST);
        }
    }

    @GetMapping("/suspicious/cancel/{id}")
    @Secured("CLIENT")
    public ResponseEntity<?> CancelSuspicious(@PathVariable("id") String transactionId){
        Map<String, String> responseObject = new HashMap<>();
        try{
            transactionService.cancelSuspiciousTransaction(transactionId);
            return new ResponseEntity<>(OK);
        } catch(SuspiciousTransactionMissingException e){
            responseObject.put("error", "Suspicious transaction missing!");
            return new ResponseEntity<>(responseObject, BAD_REQUEST);
        }
    }

}
