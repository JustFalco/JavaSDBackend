package nl.bd.sdbackendopdracht.configurations;

import nl.bd.sdbackendopdracht.enums.RoleEnums;
import nl.bd.sdbackendopdracht.models.Student;
import nl.bd.sdbackendopdracht.repos.StudentRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

//@Configuration
//public class StudentConfig {
//
//    @Bean
//    CommandLineRunner commandLineRunner(
//            StudentRepo studentRepo
//    ){
//        return args -> {
//            Student falco = new Student(
//                    1L,
//                    "Falco",
//                    "",
//                    "Wolkorte",
//                    RoleEnums.ROLE_ADMIN,
//                    "Falco@wolkorte.nl",
//                    LocalDate.now(),
//                    LocalDate.now(),
//                    "TestWachtwoord",
//                    2135,
//                    2021,
//                    true
//            );
//
//            studentRepo.save(falco);
//        };
//    }
//}
