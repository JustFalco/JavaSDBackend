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
import java.util.*;

/**
 *
 */
@Service
@AllArgsConstructor
public class GradeService implements UserDetailsService {
    private final TasksService tasksService;
    private final GradeRepository gradeRepository;
    private final UserService userService;
    private final CourseService courseService;
    private final UserRepository userRepository;

    private final NumValidation validation = new NumValidation();

    //Get one grade
    public StudentGrades getStudentGrade(Long gradeId){
        StudentGrades studentGrades = null;
        if(gradeRepository.findById(gradeId).isEmpty()){
            throw new GradeNotFoundExeption("Grade with id: " + gradeId + " has not been found!");
        }else{
            studentGrades = gradeRepository.findById(gradeId).get();
        }
        return studentGrades;
    }

    //get all grades from student
    public List<StudentGrades> getGradesFromStudent(Long studentId){
        User student = userService.getUserByUserId(studentId);
        return student.getGrades();
    }

    //Get last 15 grades
    public List<StudentGrades> getLastFifteenGrades(Long studentId){
        List<StudentGrades> allGrades = getGradesFromStudent(studentId);
        List<StudentGrades> lastFifteen = allGrades;
        if(allGrades.size() > 15){
            lastFifteen = allGrades.subList(allGrades.size()-15,allGrades.size());
        }
        Collections.reverse(lastFifteen);
        return lastFifteen;
    }

    //change one grade
    public StudentGrades changeGrade(GradeRegistrationRequest request, Long gradeId, Authentication authentication, Long studentId){
        Task task = null;
        StudentGrades grade = null;
        try {
            task = tasksService.getTask(request.getMarkBelongsToTaskId());
        } catch (Exception e) {
            task = null;
            throw new GradeProcessExeption("Could not change grade: " + e.getMessage());
        }finally {

            User teacher = null;
            User student = null;
            try {
                grade = getStudentGrade(gradeId);
                teacher = userService.getPersonalUserDetails(authentication.getName());
                student = userService.getUserByUserId(studentId);
            } catch (Exception e) {
                throw new GradeProcessExeption("could not change grade: " + e.getMessage());
            }
            finally {
                if(validation.validateFloat(request.getGrade(), 0, 20)){
                    grade.setGrade(request.getGrade());
                }
                if(!request.getDescription().isEmpty()){
                    grade.setDescription(request.getDescription());
                }
                if(validation.validateNumber(request.getWeight(), 1, 20)){
                    grade.setWeight(request.getWeight());
                }
                if(request.getTestDate() != null){
                    grade.setTestDate(request.getTestDate());
                }

                grade.setInsertionDate(LocalDateTime.now());
                grade.setSubmittedByTeacher(teacher);
                grade.setMarkBelongsToStudent(student);
                grade.setMarkBelongsToTask(task);
            }
        }

        return gradeRepository.save(grade);
    }

    //delete grade
    public void deleteGrade(Long gradeId){
        if(gradeRepository.findById(gradeId).isEmpty()){
            throw new GradeNotFoundExeption("Grade with id: " + gradeId + " cannot be deleted because it cannot be found in the database");
        }
        gradeRepository.deleteById(gradeId);
    }

    //Submit single grade
    public StudentGrades submitGrade(Long studentId, GradeRegistrationRequest request, Authentication authentication){
        if(!validation.validateId(studentId)){
            throw new NumberFormatException("Id: " + studentId + " is an illegal number");
        }
        if(!validation.validateNumber(request.getWeight(), 1, 10) || !validation.validateFloat(request.getGrade(), 0, 20)){
            throw new NumberFormatException("Illegal number input");
        }

        User teacher = userService.getPersonalUserDetails(authentication.getName());
        User gradeBelongsToStudent = userService.getUserByUserId(studentId);
        Task task = tasksService.getTask(request.getMarkBelongsToTaskId());

        StudentGrades grade = StudentGrades.builder()
                .description(request.getDescription())
                .grade(request.getGrade())
                .insertionDate(LocalDateTime.now())
                .markBelongsToStudent(gradeBelongsToStudent)
                .markBelongsToTask(task)
                .submittedByTeacher(teacher)
                .testDate(request.getTestDate())
                .build();

        return gradeRepository.save(grade);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User does not exists"));
    }
}
