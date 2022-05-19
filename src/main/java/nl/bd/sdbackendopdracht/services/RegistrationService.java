package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.Mail;
import nl.bd.sdbackendopdracht.models.datamodels.School;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.models.requestmodels.SchoolRegistrationRequest;
import nl.bd.sdbackendopdracht.models.requestmodels.UserRegistrationRequest;
import nl.bd.sdbackendopdracht.repositories.SchoolRepository;
import nl.bd.sdbackendopdracht.repositories.UserRepository;
import nl.bd.sdbackendopdracht.security.enums.RoleEnums;
import nl.bd.sdbackendopdracht.security.exeptions.EmailAlreadyExistsExeption;
import nl.bd.sdbackendopdracht.security.exeptions.SchoolAlreadyExistsExeption;
import nl.bd.sdbackendopdracht.security.mail.MailSender;
import nl.bd.sdbackendopdracht.security.validation.EmailValidation;
import nl.bd.sdbackendopdracht.security.validation.PasswordValidation;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class RegistrationService implements UserDetailsService {

    /* Validation imports */
    private final EmailValidation emailValidation;
    private final PasswordValidation passwordValidation;

    /* Repository imports */
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;

    /* Service imports */
    private final MailSender mailSender;

    /* Other imports */
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final static Mail mail = new Mail();

    public User registerSchool(SchoolRegistrationRequest request) {

        emailValidation.validate(request.schoolMail());
        boolean isValidEmail = schoolRepository.finBySchoolMail(request.schoolMail()).isPresent();
        boolean isValidName = schoolRepository.findBySchoolName(request.schoolName().toUpperCase()).isPresent();

        if (isValidEmail) {
            throw new SchoolAlreadyExistsExeption("Email: " + request.schoolMail() + "  already exists");
        }
        if (isValidName) {
            throw new SchoolAlreadyExistsExeption("Name: " + request.schoolName() + "  already exists");
        }


        School school = School.builder()
                .schoolMail(request.schoolMail())
                .schoolName(request.schoolName().toUpperCase())
                .build();

        schoolRepository.save(school);

        String password = RandomStringUtils.random(14, true, false);

        String encodedPassword = bCryptPasswordEncoder.encode(password);

        User tempUser = User.builder()
                .email(request.schoolMail())
                .password(encodedPassword)
                .roleEnums(RoleEnums.ADMINISTRATOR)
                .dateOfCreation(LocalDate.now())
                .locked(false)
                .enabled(true)
                .build();

        userRepository.save(tempUser);

        mailSender.send(request.schoolMail(), mail.getMail(request.schoolName(), request.schoolMail(), password));


        return tempUser;
    }

    public User registerStudent(UserRegistrationRequest request) {
        emailValidation.validate(request.email());
        passwordValidation.validate(request.password());

        boolean isValidEmail = userRepository.findUserByEmail(request.email()).isPresent();

        if (isValidEmail) {
            throw new EmailAlreadyExistsExeption("Email: " + request.email() + "  already exists");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(request.password());
        User student = User.builder()
                .firstName(request.firstName())
                .middleName(request.middleName())
                .lastName(request.lastName())
                .roleEnums(RoleEnums.STUDENT)
                .email(request.email())
                .dateOfBirth(request.dateOfBirth())
                .dateOfCreation(LocalDate.now())
                .password(encodedPassword)
                .year(request.studentYear())
                .locked(false)
                .enabled(true)
                .build();

        return userRepository.save(student);
    }


    public User registerAdministrator(UserRegistrationRequest request) {
        emailValidation.validate(request.email());
        passwordValidation.validate(request.password());

        boolean isValidEmail = userRepository.findUserByEmail(request.email()).isPresent();

        if (isValidEmail) {
            throw new EmailAlreadyExistsExeption("Email: " + request.email() + "  already exists");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(request.password());
        User administrator = User.builder()
                .firstName(request.firstName())
                .middleName(request.middleName())
                .lastName(request.lastName())
                .roleEnums(RoleEnums.ADMINISTRATOR)
                .email(request.email())
                .dateOfBirth(request.dateOfBirth())
                .dateOfCreation(LocalDate.now())
                .password(encodedPassword)
                .locked(false)
                .enabled(true)
                .build();

        return userRepository.save(administrator);
    }

    public User registerTeacher(UserRegistrationRequest request) {
        emailValidation.validate(request.email());
        passwordValidation.validate(request.password());

        boolean isValidEmail = userRepository.findUserByEmail(request.email()).isPresent();

        if (isValidEmail) {
            throw new EmailAlreadyExistsExeption("Email: " + request.email() + "  already exists");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(request.password());
        User administrator = User.builder()
                .firstName(request.firstName())
                .middleName(request.middleName())
                .lastName(request.lastName())
                .roleEnums(RoleEnums.TEACHER)
                .email(request.email())
                .dateOfBirth(request.dateOfBirth())
                .dateOfCreation(LocalDate.now())
                .password(encodedPassword)
                .locked(false)
                .enabled(true)
                .build();

        return userRepository.save(administrator);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User does not exists"));
    }
}
