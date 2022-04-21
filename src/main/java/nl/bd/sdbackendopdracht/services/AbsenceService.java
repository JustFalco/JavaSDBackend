package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.Absence;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.models.requestmodels.AbsenceRegistrationRequest;
import nl.bd.sdbackendopdracht.repositories.AbsenceRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class AbsenceService {

    private final AbsenceRepository absenceRepository;
    private final UserService userService;
    private final CourseService courseService;

    //Submit absence
    public Absence submitAbsence(AbsenceRegistrationRequest request, Authentication authentication){
        User absentStudent = userService.getUserByUserId(request.getAbsentStudent());
        User administrator = userService.getPersonalUserDetails(authentication.getName());

        Absence absence = Absence.builder()
                .absenceDescription(request.getAbsenceDescription())
                .absentStudent(absentStudent)
                .submittedByAdministrator(administrator)
                .absenceType(request.getAbsenceType())
                .build();

        return absenceRepository.save(absence);
    }

    //Get absence details
    public Absence getAbsenceDetails(Long absenceId){
        //TODO validation
        return absenceRepository.findById(absenceId).get();
    }

    //Remove absence
    public void removeAbsence(Long absenceId){
        absenceRepository.deleteById(absenceId);
    }

    //Change absence
    public Absence changeAbsence(Long absenceId, AbsenceRegistrationRequest request, Authentication authentication){
        Absence absence = getAbsenceDetails(absenceId);
        User student = userService.getUserByUserId(request.getAbsentStudent());
        User administrator = userService.getPersonalUserDetails(authentication.getName());
        absence.setAbsenceType(request.getAbsenceType());
        absence.setAbsenceDescription(request.getAbsenceDescription());
        absence.setAbsentStudent(student);
        absence.setSubmittedByAdministrator(administrator);

        return absenceRepository.save(absence);
    }

    //Get all absence from course
    //TODO AFMAKEN
    public List<Absence> getAllAbsenceFromCourse(Long courseId){
        Set<User> studentsInCourse = courseService.getCourse(courseId).getStudentsFollowingCourse();
        return null;
    }

    //Get all absence from school

}
