package nl.bd.sdbackendopdracht.configurations;

import nl.bd.sdbackendopdracht.enums.RoleEnums;
import nl.bd.sdbackendopdracht.models.Student;
import nl.bd.sdbackendopdracht.models.User;
import nl.bd.sdbackendopdracht.repos.DeveloperRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DeveloperAdminConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(
            DeveloperRepo developerRepo
    ){
        return args -> {
            if(!developerRepo.existsById(2L)){
                User admin = new User(
                        "Admin",
                        "Falco",
                        "Wolkorte",
                        RoleEnums.ROLE_ADMIN,
                        "Falco@wolkorte.nl",
                        LocalDate.now(),
                        LocalDate.now(),
                        "Admin456"
                );

                developerRepo.save(admin);
            }

        };
    }
}
