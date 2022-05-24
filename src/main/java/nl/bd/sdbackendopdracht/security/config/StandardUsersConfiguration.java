package nl.bd.sdbackendopdracht.security.config;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.School;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.repositories.SchoolRepository;
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
@Profile("prod")
public class  StandardUsersConfiguration {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    CommandLineRunner commandLineRunner(
            UserRepository userRepository,
            SchoolRepository schoolRepository
    ) {
        return args -> {
            if (userRepository.findByAdminRoleEnum(RoleEnums.DEVELOPER).isEmpty()) {
                //Admin user
                User admin = User.builder()
                        .firstName("Admin")
                        .email("Admin")
                        .locked(false)
                        .enabled(true)
                        .password(bCryptPasswordEncoder.encode("StrongP@ssword123"))
                        .roleEnums(RoleEnums.DEVELOPER)
                        .dateOfCreation(LocalDate.now())
                        .dateOfBirth(LocalDate.of(2001, Month.OCTOBER, 29))
                        .build();

                //School
                School school = School.builder()
                        .schoolMail("novi@education.nl")
                        .schoolName("Novi")
                        .build();

                //Student
                User student = User.builder()
                        .firstName("Falco")
                        .lastName("Wolkorte")
                        .email("falco@wolkorte.nl")
                        .year(1)
                        .dateOfCreation(LocalDate.now())
                        .dateOfBirth(LocalDate.of(2001, Month.OCTOBER, 29))
                        .school(school)
                        .password(bCryptPasswordEncoder.encode("StrongP@ssword123"))
                        .enabled(true)
                        .locked(false)
                        .roleEnums(RoleEnums.STUDENT)
                        .build();

                //Teacher
                User teacher = User.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .email("john@doe.nl")
                        .dateOfCreation(LocalDate.now())
                        .dateOfBirth(LocalDate.of(2002, Month.OCTOBER, 30))
                        .school(school)
                        .password(bCryptPasswordEncoder.encode("StrongP@ssword123"))
                        .enabled(true)
                        .locked(false)
                        .roleEnums(RoleEnums.TEACHER)
                        .build();

                //Administrator
                User administrator = User.builder()
                        .firstName("Jane")
                        .lastName("Doe")
                        .email("jane@doe.nl")
                        .dateOfCreation(LocalDate.now())
                        .dateOfBirth(LocalDate.of(2003, Month.SEPTEMBER, 30))
                        .school(school)
                        .password(bCryptPasswordEncoder.encode("StrongP@ssword123"))
                        .enabled(true)
                        .locked(false)
                        .roleEnums(RoleEnums.ADMINISTRATOR)
                        .build();

                schoolRepository.save(school);
                userRepository.save(admin);
                userRepository.save(student);
                userRepository.save(teacher);
                userRepository.save(administrator);
            }

        };
    }
}
