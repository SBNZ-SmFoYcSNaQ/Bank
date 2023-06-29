package riders.bank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import riders.bank.dto.CreditCreatedDTO;
import riders.bank.dto.CreditDTO;
import riders.bank.model.Credit;
import riders.bank.service.CreditService;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/credit")
public class CreditController {
    private final CreditService creditService;

    @Secured("BANKING_OFFICER")
    @PostMapping
    public ResponseEntity<?> requestCredit(@RequestBody CreditDTO credit) {
        CreditCreatedDTO dto = creditService.requestCredit(credit);
        return new ResponseEntity<>(dto, OK);
    }

    @Secured("BANKING_OFFICER")
    @PutMapping("/approve/{creditId}")
    public ResponseEntity<?> setApproved(@PathVariable String creditId) {
        creditService.setStatus(creditId, Credit.Status.IN_PROGRESS);
        return new ResponseEntity<>(OK);
    }

    @Secured("BANKING_OFFICER")
    @PutMapping("/reject/{creditId}")
    public ResponseEntity<?> setRejected(@PathVariable String creditId) {
        creditService.setStatus(creditId, Credit.Status.REJECTED);
        return new ResponseEntity<>(OK);
    }
}
