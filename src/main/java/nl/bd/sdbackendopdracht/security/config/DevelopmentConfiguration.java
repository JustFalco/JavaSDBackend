package nl.bd.sdbackendopdracht.security.config;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.repositories.UserRepository;
import nl.bd.sdbackendopdracht.security.enums.RoleEnums;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.Month;

@Configuration
@AllArgsConstructor
@Profile("dev")
public class DevelopmentConfiguration {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    CommandLineRunner commandLineRunner(
            UserRepository userRepository
    ) {
        return args -> {
            if (userRepository.findByAdminRoleEnum(RoleEnums.DEVELOPER).isEmpty()) {
                User admin = User.builder()
                        .firstName("Admin")
                        .email("Admin")
                        .locked(false)
                        .enabled(true)
                        .password(bCryptPasswordEncoder.encode("SuperStrongP@ssword123"))
                        .roleEnums(RoleEnums.DEVELOPER)
                        .dateOfCreation(LocalDate.now())
                        .dateOfBirth(LocalDate.of(2001, Month.OCTOBER, 29))
                        .build();


                userRepository.save(admin);
            }

        };
    }
}
