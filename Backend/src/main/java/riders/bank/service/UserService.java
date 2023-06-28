package riders.bank.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import riders.bank.App;
import riders.bank.dto.ClientDTO;
import riders.bank.dto.RegisterBodyDTO;
import riders.bank.exception.EmailExistsException;
import riders.bank.exception.InvalidDataFormatException;
import riders.bank.exception.ObjectMappingException;
import riders.bank.mapper.ObjectsMapper;
import riders.bank.model.BankingOfficer;
import riders.bank.model.Client;
import riders.bank.model.User;
import riders.bank.model.enums.Role;
import riders.bank.repository.BankingOfficerRepository;
import riders.bank.repository.ClientRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;
    private final BankingOfficerRepository bankingOfficerRepository;


    public List<ClientDTO> getAllClientDTOs() {
        List<Client> clients = clientRepository.findAll();

        return convertClientsToClientDTOS(clients);
    }

    private List<ClientDTO> convertClientsToClientDTOS(List<Client> clients) {
        List<ClientDTO> clientDTOS = new ArrayList<>();
        for (var client: clients) {
            clientDTOS.add(new ClientDTO(client.getId(), client.getFirstname() + " " + client.getLastname()));
        }
        return clientDTOS;
    }

    public User getUserBy(String email) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(email).orElse(null);
        if (client != null) {
            return client;
        }

        BankingOfficer bankingOfficer = bankingOfficerRepository.findByEmail(email).orElse(null);
        if (bankingOfficer != null) {
            return bankingOfficer;
        }
        throw new UsernameNotFoundException("user not found in the database");
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = getUserBy(username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRoleAsString()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public void saveClient(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.save(client);
    }

    public void registerClient(RegisterBodyDTO registerBodyDTO) throws
            ObjectMappingException, EmailExistsException, InvalidDataFormatException {

        Client client;
        try {
            client = ObjectsMapper.convertRegisterBodyDTOToClient(registerBodyDTO);
        } catch (Exception e) {
            App.LOGGER.error(e.getMessage());
            throw new ObjectMappingException("user");
        }
        if (isAnyFieldEmpty(client)){
            throw new InvalidDataFormatException();
        }

        if (clientRepository.findByEmail(client.getEmail()).isPresent()) {
            throw new EmailExistsException();
        }
        client.setRole(Role.CLIENT);
        saveClient(client);
    }

    private boolean isAnyFieldEmpty(Client client) {
        return !StringUtils.hasLength(client.getFirstname()) ||
                !StringUtils.hasLength(client.getLastname()) ||
                !StringUtils.hasLength(client.getEmail()) ||
                !StringUtils.hasLength(client.getPassword()) ||
                !StringUtils.hasLength(client.getAddress()) ||
                client.getBirthdate() == null ||
                !StringUtils.hasLength(client.getPhone()) ||
                client.getSex() == null;
    }
}
