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
import nl.bd.sdbackendopdracht.security.validation.InputValidation;
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
    private final InputValidation inputValidation;

    /* Repository imports */
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;

    /* Service imports */
    private final MailSender mailSender;

    /* Other imports */
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final static Mail mail = new Mail();

    /**
     * De registerSchool methode begint met het valideren van gebruikers input. Vervolgens wordt er gekeken of de school al bestaat in de database.
     * Als dit niet het geval is worden er een school object en tijdelijk gebruikers object aangemaakt. Deze worden via de mail service naar de gebruiker
     * terug gestuurd, en daarna opgeslagen in de database
     * @param request -> een DTO met school registratie gegevens
     * @return Een User object met de gegevens van de tijdelijke gebruiker
     */
    public User registerSchool(SchoolRegistrationRequest request) {

        emailValidation.validate(request.schoolMail());
        inputValidation.validate(request.schoolName());

        boolean isValidEmail = schoolRepository.finBySchoolMail(request.schoolMail()).isEmpty();
        boolean isValidName = schoolRepository.findBySchoolName(request.schoolName().toUpperCase()).isEmpty();

        if (!isValidEmail || !isValidName) {
            throw new SchoolAlreadyExistsExeption("School already exists in database!");
        }

        String password = RandomStringUtils.random(14, true, false);

        String encodedPassword = bCryptPasswordEncoder.encode(password);

        School school = School.builder()
                .schoolMail(request.schoolMail())
                .schoolName(request.schoolName().toUpperCase())
                .build();

        User tempUser = User.builder()
                .email(request.schoolMail())
                .password(encodedPassword)
                .roleEnums(RoleEnums.ADMINISTRATOR)
                .dateOfCreation(LocalDate.now())
                .locked(false)
                .enabled(true)
                .build();



        mailSender.send(request.schoolMail(), mail.getMail(request.schoolName(), request.schoolMail(), password));

        userRepository.save(tempUser);
        schoolRepository.save(school);

        return tempUser;
    }

    /**
     * De registerStudent method bouwt op basis van de UserRegistrationRequest DTO een student gebruiker,
     * en slaat deze vervolgens op in de database
     * @param request -> een DTO met gebruikers gegevens
     * @return Een User object als het aanmaken succesvol is, anders een error
     */
    public User registerStudent(UserRegistrationRequest request) {
        standardValidation(request);

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

    /**
     * De registerAdministrator method bouwt op basis van de UserRegistrationRequest DTO een administrator gebruiker,
     * en slaat deze vervolgens op in de database
     * @param request -> een DTO met gebruikers gegevens
     * @return Een User object als het aanmaken succesvol is, anders een error
     */
    public User registerAdministrator(UserRegistrationRequest request) {
        standardValidation(request);

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

    /**
     * De registerTeacher method bouwt op basis van de UserRegistrationRequest DTO een teacher gebruiker,
     * en slaat deze vervolgens op in de database
     * @param request -> een DTO met gebruikers gegevens
     * @return Een User object als het aanmaken succesvol is, anders een error
     */
    public User registerTeacher(UserRegistrationRequest request) {
        standardValidation(request);

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

    /**
     * Validatie van user input zoals email en wachtwoord, wordt standaard uitgevoerd vóór het aanmaken van een gebruiker
     * @param request -> een DTO met gebruikers gegevens
     */
    private void standardValidation(UserRegistrationRequest request) {
        emailValidation.validate(request.email());
        passwordValidation.validate(request.password());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User does not exists"));
    }
}
