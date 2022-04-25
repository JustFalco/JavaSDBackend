package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.StudentGrades;
import nl.bd.sdbackendopdracht.models.datamodels.Task;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.models.requestmodels.GradeRegistrationRequest;
import nl.bd.sdbackendopdracht.repositories.GradeRepository;
import nl.bd.sdbackendopdracht.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GradeService implements UserDetailsService {
    private final TasksService tasksService;
    private final GradeRepository gradeRepository;
    private final UserService userService;
    private final CourseService courseService;
    private final UserRepository userRepository;
    //Get one grade
    public StudentGrades getStudentGrade(Long gradeId){
        StudentGrades studentGrades = null;
        //TODO validation
        if(gradeRepository.findById(gradeId).isPresent()){
            studentGrades = gradeRepository.findById(gradeId).get();
        }
        return studentGrades;
    }

    //get all grades from student
    public List<StudentGrades> getGradesFromStudent(Long studentId){
        User student = userService.getUserByUserId(studentId);
        return student.getGrades();
    }

    //get all grades in course
//    public List<List<StudentGrades>> getGradesInCourse(Long courseId){
//        List<User> allStudentsInCourse = courseService.getStudentsOnCourse(courseId);
//        List<List<StudentGrades>> allStudentGradesInCourse = new List<List<>>();
//        //TODO validation
//        for (User user : allStudentsInCourse){
//            allStudentGradesInCourse.add(user.getGrades());
//        }
//
//        return allStudentGradesInCourse;
//    }

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
        if(request.getMarkBelongsToTaskId() != null){
            if(request.getMarkBelongsToTaskId() != 0){
                task = tasksService.getTask(request.getMarkBelongsToTaskId());
            }
        }

        //TODO validation
        StudentGrades grade = getStudentGrade(gradeId);
        User teacher = userService.getPersonalUserDetails(authentication.getName());
        User student = userService.getUserByUserId(studentId);

        grade.setGrade(request.getGrade());
        grade.setDescription(request.getDescription());
        grade.setInsertionDate(LocalDateTime.now());
        grade.setSubmittedByTeacher(teacher);
        grade.setMarkBelongsToStudent(student);
        grade.setMarkBelongsToTask(task);
        grade.setWeight(request.getWeight());
        grade.setTestDate(request.getTestDate());

        return gradeRepository.save(grade);
    }

    //TODO submit grade for all students in course

    //delete grade
    public void deleteGrade(Long gradeId){
        gradeRepository.deleteById(gradeId);
    }

    //TODO Submit grade for task

    //Submit single grade
    public StudentGrades submitGrade(Long studentId, GradeRegistrationRequest request, Authentication authentication){
        //TODO heel veel validation
        User teacher = userService.getPersonalUserDetails(authentication.getName());
        User gradeBelongsToStudent = userService.getUserByUserId(studentId);
        Task task = null;
        if(request.getMarkBelongsToTaskId() != null){
            if(request.getMarkBelongsToTaskId() != 0){
                task = tasksService.getTask(request.getMarkBelongsToTaskId());
            }
        }



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
