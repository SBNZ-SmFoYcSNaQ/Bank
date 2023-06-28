package riders.bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import riders.bank.mapper.ObjectsMapper;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class App 
{
    public static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
        ObjectsMapper.initializeObjectMapper();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
