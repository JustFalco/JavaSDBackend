package nl.bd.sdbackendopdracht.security.config;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.repositories.UserRepository;
import nl.bd.sdbackendopdracht.security.enums.RoleEnums;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.Month;

@Configuration
@AllArgsConstructor
public class DeveloperAdminConfiguration {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    CommandLineRunner commandLineRunner(
            UserRepository userRepository
    ){
        return args -> {
            if(userRepository.findByAdminRoleEnum(RoleEnums.DEVELOPER).isEmpty()){
                User admin = User.builder()
                        .firstName("Admin")
                        .email("falco@wolkorte.nl")
                        .locked(false)
                        .enabled(true)
                        .password(bCryptPasswordEncoder.encode("Falco567"))
                        .roleEnums(RoleEnums.DEVELOPER)
                        .dateOfCreation(LocalDate.now())
                        .dateOfBirth(LocalDate.of(2001, Month.OCTOBER, 29))
                        .build();


                userRepository.save(admin);
            }

        };
    }
}
