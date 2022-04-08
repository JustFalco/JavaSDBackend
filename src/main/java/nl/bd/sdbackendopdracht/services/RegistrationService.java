package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.SchoolRegistrationRequest;
import nl.bd.sdbackendopdracht.models.datamodels.School;
import nl.bd.sdbackendopdracht.repositories.SchoolRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final SchoolRepository schoolRepository;


    public String registerSchool(SchoolRegistrationRequest request) {
        School school = School.builder()
                .schoolMail(request.getSchoolMail())
                .schoolName(request.getSchoolName())
                .build();

        schoolRepository.save(school);
        return "School saved";
    }
}
