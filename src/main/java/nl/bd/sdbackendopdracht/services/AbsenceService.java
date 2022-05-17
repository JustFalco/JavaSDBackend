package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.Absence;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.models.requestmodels.AbsenceRegistrationRequest;
import nl.bd.sdbackendopdracht.repositories.AbsenceRepository;
import nl.bd.sdbackendopdracht.security.exeptions.AbsenceNotFoundExeption;
import nl.bd.sdbackendopdracht.security.exeptions.AbsenceProcessorExeption;
import nl.bd.sdbackendopdracht.security.exeptions.UserNotFoundExeption;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class AbsenceService {

    private final AbsenceRepository absenceRepository;
    private final UserService userService;
    private final CourseService courseService;
    private final SchoolService schoolService;

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
        Absence absence = null;
        if(absenceRepository.findById(absenceId).isEmpty()){
            throw new AbsenceNotFoundExeption("Absence with id: " + absenceId + " has not been found!");
        }else{
            absence = absenceRepository.findById(absenceId).get();
        }
        return absence;
    }

    //Remove absence
    public void removeAbsence(Long absenceId){
        if(absenceRepository.findById(absenceId).isEmpty()){
            throw new AbsenceNotFoundExeption("Absence cannot be deleted because it is not found in the database!");
        }
        absenceRepository.deleteById(absenceId);
    }

    //Change absence
    public Absence changeAbsence(Long absenceId, AbsenceRegistrationRequest request, Authentication authentication){
        Absence absence = getAbsenceDetails(absenceId);

        try {
            User student = userService.getUserByUserId(request.getAbsentStudent());
            User administrator = userService.getPersonalUserDetails(authentication.getName());
            if (request.getAbsenceType() != null) {
                absence.setAbsenceType(request.getAbsenceType());
            }
            if (request.getAbsenceDescription() != null && request.getAbsenceDescription() != "") {
                absence.setAbsenceDescription(request.getAbsenceDescription());
            }
            if(student != null){
                absence.setAbsentStudent(student);
            }
            if(administrator != null){
                absence.setSubmittedByAdministrator(administrator);
            }


        }catch (UserNotFoundExeption userNotFoundExeption){
            throw new AbsenceProcessorExeption("Abcense could not be changed: " + userNotFoundExeption.getMessage());
        }

        Absence save = absenceRepository.save(absence);
        return save;
    }

    //Get all absence from course
    public List<Set<Absence>> getAllAbsenceFromCourse(Long courseId){
        Set<User> studentsInCourse = courseService.getCourse(courseId).getStudentsFollowingCourse();
        List<Set<Absence>> absenceList = new ArrayList<>();
        for (User student : studentsInCourse){
            absenceList.add(getAbsenceFromStudent(student.getUserId()));
        }
        return absenceList;
    }

    //Get all absence from school
    public List<Set<Absence>> getAllAbsenceFromSchool(Long schoolId){
        Set<User> studentsInSchool = schoolService.getAllStudentsOnSchool(schoolId);
        List<Set<Absence>> absenceList = new ArrayList<>();

        for (User student : studentsInSchool){
            absenceList.add(getAbsenceFromStudent(student.getUserId()));
        }

        return absenceList;
    }

    //Get all absence from student (All)
    public Set<Absence> getAbsenceFromStudent(Long userId){
        return absenceRepository.getALlAbsenceFromUser(userId).orElseThrow(() -> new IllegalStateException("No things found"));
    }
}
