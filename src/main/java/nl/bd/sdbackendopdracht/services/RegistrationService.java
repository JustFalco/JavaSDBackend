package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.Mail;
import nl.bd.sdbackendopdracht.models.SchoolRegistrationRequest;
import nl.bd.sdbackendopdracht.models.StudentRegistrationRequest;
import nl.bd.sdbackendopdracht.models.datamodels.School;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.repositories.SchoolRepository;
import nl.bd.sdbackendopdracht.repositories.UserRepository;
import nl.bd.sdbackendopdracht.security.enums.RoleEnums;
import nl.bd.sdbackendopdracht.security.exeptions.EmailAlreadyExistsExeption;
import nl.bd.sdbackendopdracht.security.mail.MailSender;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.Random;

@Service
@AllArgsConstructor
public class RegistrationService {

    /* Repository imports */

    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;

    /* Service imports */
    private final MailSender mailSender;

    /* Other imports */
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final static Mail mail = new Mail();

    public String registerSchool(SchoolRegistrationRequest request) {
        School school = School.builder()
                .schoolMail(request.getSchoolMail())
                .schoolName(request.getSchoolName())
                .build();

        schoolRepository.save(school);

        String password = RandomStringUtils.random(14, true, false);

        mailSender.send(request.getSchoolMail(), mail.getMail(request.getSchoolName(), request.getSchoolMail(), password));

        return "School saved";
    }

    public String registerStudent(StudentRegistrationRequest request){
        boolean isValidEmail = userRepository.findUserByEmail(request.getEmail()).isPresent();

        if(isValidEmail){
            throw new EmailAlreadyExistsExeption("Email: " + request.getEmail() + "  already exists");
        }
        //TODO create salt
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


        userRepository.save(student);

        return "Student saved!";
    }

    public String registerAdministrator(){
        return "Administrator registerd";
    }

    public String registerTeacher(){
        return "Teacher registerd";
    }
}
