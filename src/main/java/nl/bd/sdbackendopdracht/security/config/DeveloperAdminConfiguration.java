package nl.bd.sdbackendopdracht.security.config;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.security.enums.RoleEnums;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.repositories.DeveloperRepository;
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
            DeveloperRepository developerRepository
    ){
        return args -> {
            if(developerRepository.findByAdminRoleEnum().isEmpty()){
                User admin = new User(
                        "Admin",
                        "",
                        "",
                        RoleEnums.DEVELOPER,
                        "falco@wolkorte.nl",
                        LocalDate.now(),
                        LocalDate.of(2001, Month.OCTOBER, 29),
                        bCryptPasswordEncoder.encode("Falco567")

                );


                developerRepository.save(admin);
            }

        };
    }
}
