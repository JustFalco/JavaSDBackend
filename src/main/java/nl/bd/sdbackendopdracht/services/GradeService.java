package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.StudentGrades;
import nl.bd.sdbackendopdracht.models.datamodels.Task;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.models.requestmodels.GradeRegistrationRequest;
import nl.bd.sdbackendopdracht.repositories.GradeRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class GradeService extends SuperService {
    private final TasksService tasksService;
    private final GradeRepository gradeRepository;
    private final UserService userService;
    private final CourseService courseService;

    //Get one grade
    public StudentGrades getStudentGrade(Long gradeId){
        //TODO validation
        return gradeRepository.getById(gradeId);
    }

    //get all grades from student
    public Set<StudentGrades> getGradesFromStudent(Long studentId){
        User student = userService.getUserByUserId(studentId);
        return student.getGrades();
    }

    //get all grades in course
    public Set<Set<StudentGrades>> getGradesInCourse(Long courseId){
        Set<User> allStudentsInCourse = courseService.getStudentsOnCourse(courseId);
        Set<Set<StudentGrades>> allStudentGradesInCourse = new HashSet<>();
        //TODO validation
        for (User user : allStudentsInCourse){
            allStudentGradesInCourse.add(user.getGrades());
        }

        return allStudentGradesInCourse;
    }

    //change one grade
    public StudentGrades changeGrade(GradeRegistrationRequest request, Long gradeId, Authentication authentication){
        //TODO validation
        StudentGrades grade = getStudentGrade(gradeId);
        User teacher = userService.getPersonalUserDetails(authentication.getName());
        User student = userService.getUserByUserId(request.getMarkBelongsToStudentId());
        Task task = tasksService.getTask(request.getMarkBelongsToTaskId());
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

}
