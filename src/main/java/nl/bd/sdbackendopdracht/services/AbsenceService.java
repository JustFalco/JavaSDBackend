package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.Absence;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.models.requestmodels.AbsenceRegistrationRequest;
import nl.bd.sdbackendopdracht.repositories.AbsenceRepository;
import nl.bd.sdbackendopdracht.security.enums.RoleEnums;
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

    /**
     * De submitAbsence methode bouwt op basis van de request DTO een nieuw Absence object, en slaat deze
     * vervolgens op in de database
     * @param request -> De DTO met data om een absence object te maken
     * @param authentication -> Standaard object om te kijken wie ingelogd is
     * @return Het gemaakte Absence object, anders een error
     */
    public Absence submitAbsence(AbsenceRegistrationRequest request, Authentication authentication) {
        User absentStudent = userService.getUserByUserId(request.absentStudent());

        if(absentStudent.getRoleEnums() != RoleEnums.STUDENT){
            throw new AbsenceProcessorExeption("Trying to submit absence for non student");
        }

        User administrator = userService.getPersonalUserDetails(authentication.getName());

        Absence absence = Absence.builder()
                .absenceDescription(request.absenceDescription())
                .absentStudent(absentStudent)
                .submittedByAdministrator(administrator)
                .absenceType(request.absenceType())
                .build();

        return absenceRepository.save(absence);
    }

    /**
     * De getAbsenceDetails haalt een Absence object uit de database en geeft de gegevens hiervan terug
     * @param absenceId -> id van het Absence object dat uit de database gehaald moet worden
     * @return Absence object, anders een error
     */
    public Absence getAbsenceDetails(Long absenceId) {
        Absence absence;
        if (absenceRepository.findById(absenceId).isEmpty()) {
            throw new AbsenceNotFoundExeption("Absence with id: " + absenceId + " has not been found!");
        } else {
            absence = absenceRepository.findById(absenceId).get();
        }
        return absence;
    }

    /**
     * De removeAbsence kijkt in de database of de absenceId voor komt, en verwijderd het Absence object als dit het geval is
     * @param absenceId -> id van het Absence object dat verwijderd moet worden
     */
    public void removeAbsence(Long absenceId) {
        if (absenceRepository.findById(absenceId).isEmpty()) {
            throw new AbsenceNotFoundExeption("Absence cannot be deleted because it is not found in the database!");
        }
        absenceRepository.deleteById(absenceId);
    }

    /**
     * De changeAbsence methode haalt een bestaand Absence object uit de database, en past waar nodig de gegevens aan. Als dit is gelukt wordt het
     * Absence object weer opgeslagen in de database
     * @param absenceId -> id van het Absence object wat gewijzigd moet worden
     * @param request -> een request DTO met data om het Absence object te wijzigen
     * @param authentication -> Standaard object om te kijken wie ingelogd is
     * @return Absence object, anders een error
     */
    public Absence changeAbsence(Long absenceId, AbsenceRegistrationRequest request, Authentication authentication) {
        Absence absence = getAbsenceDetails(absenceId);

        try {
            User student = userService.getUserByUserId(request.absentStudent());
            User administrator = userService.getPersonalUserDetails(authentication.getName());

            if(student.getRoleEnums() != RoleEnums.STUDENT){
                throw new AbsenceProcessorExeption("Trying to add a non student to absence");
            }

            if (request.absenceType() != null) {
                absence.setAbsenceType(request.absenceType());
            }
            if (request.absenceDescription() != null && !request.absenceDescription().equals("")) {
                absence.setAbsenceDescription(request.absenceDescription());
            }
            if (student != null) {
                absence.setAbsentStudent(student);
            }
            if (administrator != null) {
                absence.setSubmittedByAdministrator(administrator);
            }


        } catch (UserNotFoundExeption userNotFoundExeption) {
            throw new AbsenceProcessorExeption("Abcense could not be changed: " + userNotFoundExeption.getMessage());
        }

        return absenceRepository.save(absence);
    }

    public List<Set<Absence>> getAllAbsenceFromCourse(Long courseId) {
        Set<User> studentsInCourse = courseService.getCourse(courseId).getStudentsFollowingCourse();
        List<Set<Absence>> absenceList = new ArrayList<>();
        for (User student : studentsInCourse) {
            absenceList.add(getAbsenceFromStudent(student.getUserId()));
        }
        return absenceList;
    }

    //Get all absence from school
    public List<Set<Absence>> getAllAbsenceFromSchool(Long schoolId) {
        Set<User> studentsInSchool = schoolService.getAllStudentsOnSchool(schoolId);
        List<Set<Absence>> absenceList = new ArrayList<>();

        for (User student : studentsInSchool) {
            absenceList.add(getAbsenceFromStudent(student.getUserId()));
        }

        return absenceList;
    }

    /**
     * De getAbsenceFromStudent mehtode haalt een student uit de database en geeft alle Absence van deze student terug
     * @param userId -> id van de gebruiker waar de absense van obgevraagd wordt
     * @return Lijst met Absence objecten
     */
    public Set<Absence> getAbsenceFromStudent(Long userId) {
        User student = userService.getUserByUserId(userId);
        if(student.getRoleEnums() != RoleEnums.STUDENT){
            throw new AbsenceProcessorExeption("Trying to get absence from non student!");
        }
        return absenceRepository.getALlAbsenceFromUser(userId).orElseThrow(() -> new IllegalStateException("No absence found"));
    }
}
