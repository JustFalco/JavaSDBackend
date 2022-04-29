package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.Mail;
import nl.bd.sdbackendopdracht.models.datamodels.School;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.models.requestmodels.AdministratorRegistrationRequest;
import nl.bd.sdbackendopdracht.models.requestmodels.SchoolRegistrationRequest;
import nl.bd.sdbackendopdracht.models.requestmodels.StudentRegistrationRequest;
import nl.bd.sdbackendopdracht.models.requestmodels.TeacherRegistrationRequest;
import nl.bd.sdbackendopdracht.repositories.SchoolRepository;
import nl.bd.sdbackendopdracht.repositories.UserRepository;
import nl.bd.sdbackendopdracht.security.enums.RoleEnums;
import nl.bd.sdbackendopdracht.security.exeptions.EmailAlreadyExistsExeption;
import nl.bd.sdbackendopdracht.security.exeptions.IllegalEmailExeption;
import nl.bd.sdbackendopdracht.security.mail.MailSender;
import nl.bd.sdbackendopdracht.security.validation.EmailValidation;
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

    private static final String message = "This email cannot be used because of illegal characters or wrong format!";

    /* Validation imports */
    private final EmailValidation emailValidation;

    /* Repository imports */
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;

    /* Service imports */
    private final MailSender mailSender;

    /* Other imports */
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final static Mail mail = new Mail();

    public User registerSchool(SchoolRegistrationRequest request) {

        emailValidation.validate(request.getSchoolMail(), message);


        School school = School.builder()
                .schoolMail(request.getSchoolMail())
                .schoolName(request.getSchoolName())
                .build();

        schoolRepository.save(school);

        String password = RandomStringUtils.random(14, true, false);

        String encodedPassword = bCryptPasswordEncoder.encode(password);

        User tempUser = User.builder()
                .email(request.getSchoolMail())
                .password(encodedPassword)
                .roleEnums(RoleEnums.ADMINISTRATOR)
                .dateOfCreation(LocalDate.now())
                .locked(false)
                .enabled(true)
                .build();

        userRepository.save(tempUser);

        mailSender.send(request.getSchoolMail(), mail.getMail(request.getSchoolName(), request.getSchoolMail(), password));



        return tempUser;
    }

    public User registerStudent(StudentRegistrationRequest request){
        emailValidation.validate(request.getEmail(), message);

        boolean isValidEmail = userRepository.findUserByEmail(request.getEmail()).isPresent();

        if(isValidEmail){
            throw new EmailAlreadyExistsExeption("Email: " + request.getEmail() + "  already exists");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());
        User student = User.builder()
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .roleEnums(RoleEnums.STUDENT)
                .email(request.getEmail())
                .dateOfBirth(request.getDateOfBirth())
                .dateOfCreation(LocalDate.now())
                .password(encodedPassword)
                .studentNumber(request.getStudentNumber())
                .year(request.getStudentYear())
                .locked(false)
                .enabled(true)
                .build();

        return userRepository.save(student);
    }


    public User registerAdministrator(AdministratorRegistrationRequest request){
        emailValidation.validate(request.getEmail(), message);

        boolean isValidEmail = userRepository.findUserByEmail(request.getEmail()).isPresent();

        if(isValidEmail){
            throw new EmailAlreadyExistsExeption("Email: " + request.getEmail() + "  already exists");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());
        User administrator = User.builder()
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .roleEnums(RoleEnums.ADMINISTRATOR)
                .email(request.getEmail())
                .dateOfBirth(request.getDateOfBirth())
                .dateOfCreation(LocalDate.now())
                .password(encodedPassword)
                .workerNumber(request.getWorkerNumber())
                .isActiveWorker(request.isActiveWorker())
                .locked(false)
                .enabled(true)
                .build();

        return userRepository.save(administrator);
    }

    public User registerTeacher(TeacherRegistrationRequest request ){
        emailValidation.validate(request.getEmail(), message);

        boolean isValidEmail = userRepository.findUserByEmail(request.getEmail()).isPresent();

        if(isValidEmail){
            throw new EmailAlreadyExistsExeption("Email: " + request.getEmail() + "  already exists");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());
        User administrator = User.builder()
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .roleEnums(RoleEnums.TEACHER)
                .email(request.getEmail())
                .dateOfBirth(request.getDateOfBirth())
                .dateOfCreation(LocalDate.now())
                .password(encodedPassword)
                .teacherNumber(request.getTeacherNumber())
                .isActiveTeacher(request.isActiveTeacher())
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
