package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.Mail;
import nl.bd.sdbackendopdracht.models.SchoolRegistrationRequest;
import nl.bd.sdbackendopdracht.models.datamodels.School;
import nl.bd.sdbackendopdracht.repositories.SchoolRepository;
import nl.bd.sdbackendopdracht.security.mail.MailSender;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.nio.charset.Charset;
import java.util.Random;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final SchoolRepository schoolRepository;
    private final MailSender mailSender;

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

    public String registerStudent(){
        return "Student registerd";
    }

    public String registerAdministrator(){
        return "Administrator registerd";
    }

    public String registerTeacher(){
        return "Teacher registerd";
    }
}
