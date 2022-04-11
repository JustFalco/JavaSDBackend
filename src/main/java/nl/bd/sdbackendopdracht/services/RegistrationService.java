package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.Mail;
import nl.bd.sdbackendopdracht.models.SchoolRegistrationRequest;
import nl.bd.sdbackendopdracht.models.datamodels.School;
import nl.bd.sdbackendopdracht.repositories.SchoolRepository;
import nl.bd.sdbackendopdracht.security.mail.MailSender;
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

        mailSender.send(request.getSchoolMail(), mail.getMail(request.getSchoolName()));

        return "School saved";
    }
}
