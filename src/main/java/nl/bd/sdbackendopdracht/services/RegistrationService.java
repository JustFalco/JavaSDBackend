package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.Mail;
import nl.bd.sdbackendopdracht.models.SchoolRegistrationRequest;
import nl.bd.sdbackendopdracht.models.datamodels.School;
import nl.bd.sdbackendopdracht.repositories.SchoolRepository;
import nl.bd.sdbackendopdracht.security.mail.MailSender;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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

        RandomStringGenerator stringGenerator = new RandomStringGenerator.Builder().build();
        String password = stringGenerator.generate(17);
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
