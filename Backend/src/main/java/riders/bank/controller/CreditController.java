package riders.bank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<?> requestCredit(@RequestBody CreditDTO credit) {
        CreditCreatedDTO dto = creditService.requestCredit(credit);
        return new ResponseEntity<>(dto, OK);
    }

    @PutMapping("/approve/{creditId}")
    public ResponseEntity<?> setApproved(@PathVariable String creditId) {
        creditService.setStatus(creditId, Credit.Status.IN_PROGRESS);
        return new ResponseEntity<>(OK);
    }

    @PutMapping("/reject/{creditId}")
    public ResponseEntity<?> setRejected(@PathVariable String creditId) {
        creditService.setStatus(creditId, Credit.Status.REJECTED);
        return new ResponseEntity<>(OK);
    }
}
