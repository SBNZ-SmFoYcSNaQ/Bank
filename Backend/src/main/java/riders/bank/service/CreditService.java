package riders.bank.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;
import riders.bank.App;
import riders.bank.dto.CreditCreatedDTO;
import riders.bank.dto.CreditDTO;
import riders.bank.model.Client;
import riders.bank.model.Credit;
import riders.bank.model.EmploymentInfo;
import riders.bank.repository.ClientRepository;
import riders.bank.repository.CreditRepository;
import riders.bank.repository.EmploymentInfoRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CreditService {
    private final KieContainer kieContainerCredit;
    private final ClientRepository clientRepository;
    private final CreditRepository creditRepository;
    private final EmploymentInfoRepository employmentInfoRepository;

    public CreditCreatedDTO requestCredit(CreditDTO creditDTO){
        CreditCreatedDTO dto = new CreditCreatedDTO();
        UUID clientId = UUID.fromString(creditDTO.getUserId());
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client == null){
            App.LOGGER.error("User doesn't exist");
            dto.setRecommended(false);
            return dto;
        }

        EmploymentInfo employmentInfo = new EmploymentInfo(
                creditDTO.getEmploymentStartDate(),
                creditDTO.getEmploymentEndDate(),
                creditDTO.getSalary()
        );
        employmentInfoRepository.save(employmentInfo);

        Credit credit = new Credit(
                creditDTO.getAmount(),
                creditDTO.getPaymentsNumber(),
                creditDTO.getMinRepaymentPeriod(),
                creditDTO.getMaxRepaymentPeriod(),
                Credit.Status.PENDING,
                employmentInfo,
                client
        );
        creditRepository.save(credit);

        dto.setCreditId(credit.getId().toString());
        dto.setRecommended(recommendCredit(credit));
        return dto;
    }

    public Boolean recommendCredit(Credit credit){
        KieSession kieSession = kieContainerCredit.newKieSession();
        List<Credit> credits = creditRepository.findAllByClient(credit.getClient());
        for (Credit creditIter:credits) {
            kieSession.insert(creditIter);
        }
        kieSession.setGlobal("specificCredit", credit);
        kieSession.insert(credit);
        kieSession.fireAllRules();
        System.out.println("IS RECOMMENDED: " + credit.isRecommended());
        kieSession.dispose();
        return credit.isRecommended();
    }

    public void setStatus(String creditIdString, Credit.Status status) {
        UUID clientId = UUID.fromString(creditIdString);
        Credit credit = creditRepository.findById(clientId).orElse(null);
        if (credit == null) {
            App.LOGGER.error("Credit doesn't exist.");
            return;
        }
        credit.setStatus(status);
        creditRepository.save(credit);
    }
}
