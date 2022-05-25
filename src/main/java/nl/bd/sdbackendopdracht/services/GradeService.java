package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.StudentGrades;
import nl.bd.sdbackendopdracht.models.datamodels.Task;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.models.requestmodels.GradeRegistrationRequest;
import nl.bd.sdbackendopdracht.repositories.GradeRepository;
import nl.bd.sdbackendopdracht.repositories.UserRepository;
import nl.bd.sdbackendopdracht.security.exeptions.GradeNotFoundExeption;
import nl.bd.sdbackendopdracht.security.exeptions.GradeProcessExeption;
import nl.bd.sdbackendopdracht.security.validation.NumValidation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 *
 */
@Service
@AllArgsConstructor
public class GradeService implements UserDetailsService {
    private final TasksService tasksService;
    private final GradeRepository gradeRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    private final NumValidation validation = new NumValidation();

    /**
     * De getStudentGrade methode verkrijgt één Grade object uit de database en geeft deze terug
     * @param gradeId -> id van cijfer dat opgevraagd wordt
     * @return Cijfer object, anders een error
     */
    public StudentGrades getStudentGrade(Long gradeId) {
        StudentGrades studentGrades;
        if (gradeRepository.findById(gradeId).isEmpty()) {
            throw new GradeNotFoundExeption("Grade with id: " + gradeId + " has not been found!");
        } else {
            studentGrades = gradeRepository.findById(gradeId).get();
        }
        return studentGrades;
    }

    /**
     * De getGradesFromStudent verkrijgt alle behaalde cijfers van één student
     * en geeft deze terug in de vorm van een lijst
     * @param studentId -> id van de student waar de cijfers opgevraagd worden
     * @return Lijst met cijfer objecten
     */
    public List<StudentGrades> getGradesFromStudent(Long studentId) {
        User student = userService.getUserByUserId(studentId);
        return student.getGrades();
    }

    /**
     * De getLastFifteenGrades pakt de vijftien laatst behaalde cijfers van een student
     * en geeft deze terug in de vorm van een lijst
     * @param studentId -> id van de student waar de cijfers opgevraagd worden
     * @return Lijst met 15 (of minder) cijfers
     */
    public List<StudentGrades> getLastFifteenGrades(Long studentId) {
        List<StudentGrades> allGrades = getGradesFromStudent(studentId);
        List<StudentGrades> lastFifteen = allGrades;
        if (allGrades.size() > 15) {
            lastFifteen = allGrades.subList(allGrades.size() - 15, allGrades.size());
        }
        Collections.reverse(lastFifteen);
        return lastFifteen;
    }

    /**
     * De submitGrade methode valideerd eerst de gebruikers invoer. Als dit niet klopt, wordt er een error teruggegeven.
     * Anders maakt de methode op basis van de gradeId een bestaand cijfer uit de database, en veranderd met behulp van de DTO
     * de waarden in het cijfer object en slaat deze vervolgens op in de database
     * @param request -> DTO met data van het cijfer
     * @param gradeId -> id van het cijfer dat aangepast moet worden
     * @param authentication -> standaard authentication object om de huidig ingelogde gebruiker te achterhalen
     * @param studentId -> id van de student voor wie het cijfer bedoeld is
     * @return StudentGrades object, anders een error
     */
    public StudentGrades changeGrade(GradeRegistrationRequest request, Long gradeId, Authentication authentication, Long studentId) {
        Task task = null;
        StudentGrades grade = null;
        try {
            if(request.markBelongsToTaskId() != 0){
                task = tasksService.getTask(request.markBelongsToTaskId());
            }
        } catch (Exception e) {
            throw new GradeProcessExeption("Could not change grade: " + e.getMessage());
        }

        User teacher = null;
        User student = null;
        try {
            grade = getStudentGrade(gradeId);
            teacher = userService.getPersonalUserDetails(authentication.getName());
            student = userService.getUserByUserId(studentId);
        } catch (Exception e) {
            throw new GradeProcessExeption("could not change grade: " + e.getMessage());
        } finally {
            if (validation.validateFloat(request.grade(), 0, 20)) {
                assert grade != null;
                grade.setGrade(request.grade());
            }
            if (!request.description().isEmpty()) {
                assert grade != null;
                grade.setDescription(request.description());
            }
            if (validation.validateNumber(request.weight(), 1, 20)) {
                assert grade != null;
                grade.setWeight(request.weight());
            }
            if (request.testDate() != null) {
                assert grade != null;
                grade.setTestDate(request.testDate());
            }

            assert grade != null;
            grade.setInsertionDate(LocalDateTime.now());
            grade.setSubmittedByTeacher(teacher);
            grade.setMarkBelongsToStudent(student);
            grade.setMarkBelongsToTask(task);
        }


        return gradeRepository.save(grade);
    }

    /**
     * De deleteGrade methode kijkt of een cijfer op basis van id in de database staat,
     * zo ja dan wordt deze verwijderd
     * @param gradeId -> id van cijfer dat verwijderd moet worden
     */
    public void deleteGrade(Long gradeId) {
        if (gradeRepository.findById(gradeId).isEmpty()) {
            throw new GradeNotFoundExeption("Grade with id: " + gradeId + " cannot be deleted because it cannot be found in the database");
        }
        gradeRepository.deleteById(gradeId);
    }

    /**
     * De submitGrade methode valideerd eerst de gebruikers invoer. Als dit niet klopt, wordt er een error teruggegeven.
     * Anders maakt de methode op basis van de request DTO een nieuw StudentGrade object aan en slaat deze vervolgens op in de database
     * @param studentId -> id van de student voor wie het cijfer bedoeld is
     * @param request -> DTO met data van het cijfer
     * @param authentication -> standaard authentication object om de huidig ingelogde gebruiker te achterhalen
     * @return StudentGrades object, anders een error
     */
    public StudentGrades submitGrade(Long studentId, GradeRegistrationRequest request, Authentication authentication) {
        if (!validation.validateId(studentId)) {
            throw new GradeProcessExeption("Id: " + studentId + " is an illegal number");
        }
        if (!validation.validateNumber(request.weight(), 1, 10) || !validation.validateFloat(request.grade(), 0.0f, 20.0f)) {
            throw new GradeProcessExeption("Illegal number input");
        }

        User teacher = userService.getPersonalUserDetails(authentication.getName());
        User gradeBelongsToStudent = userService.getUserByUserId(studentId);

        Task task = null;
        if(request.markBelongsToTaskId() != 0){
            task = tasksService.getTask(request.markBelongsToTaskId());
        }


        StudentGrades grade = StudentGrades.builder()
                .description(request.description())
                .grade(request.grade())
                .weight(request.weight())
                .insertionDate(LocalDateTime.now())
                .markBelongsToStudent(gradeBelongsToStudent)
                .markBelongsToTask(task)
                .submittedByTeacher(teacher)
                .testDate(request.testDate())
                .build();

        return gradeRepository.save(grade);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User does not exists"));
    }
}
