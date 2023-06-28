package riders.bank.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import riders.bank.dto.RegisterBodyDTO;
import riders.bank.model.Client;

public class ObjectsMapper {
    private static final ModelMapper modelMAPPER = new ModelMapper();

    private ObjectsMapper() {}

    public static void initializeObjectMapper() {
        PropertyMap<RegisterBodyDTO, Client> registerBodyDTOtoClientMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                map().setFirstname(source.getFirstname());
                map().setLastname(source.getLastname());
                map().setEmail(source.getEmail());
                map().setPassword(source.getPassword());
                map().setAddress(source.getAddress());
                map().setPhone(source.getPhone());
                map().setSex(source.getSex());
                map().setBirthdate(source.getBirthdate());
            }
        };

        modelMAPPER.addMappings(registerBodyDTOtoClientMap);
    }

    public static Client convertRegisterBodyDTOToClient(RegisterBodyDTO registerBodyDTO) {
        return modelMAPPER.map(registerBodyDTO, Client.class);
    }

}
