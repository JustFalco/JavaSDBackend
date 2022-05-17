package nl.bd.sdbackendopdracht.controllers;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.StudentGrades;
import nl.bd.sdbackendopdracht.models.requestmodels.GradeRegistrationRequest;
import nl.bd.sdbackendopdracht.services.GradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    //Get latest 15 Grades (Student)
    @GetMapping("/student/grades/get_grade/last/student={userId}")
    public List<StudentGrades> getLastFifteenGrades(@PathVariable("userId") Long userId){
        return gradeService.getLastFifteenGrades(userId);
    }

    //Get all grades from student (All)
        @GetMapping("/grades/get_grade/overview/student={userId}")
    public List<StudentGrades> getGradeOverview(
            @PathVariable("userId") Long userId
        ){
        return gradeService.getGradesFromStudent(userId);
    }

    //Get grade details (All)
    @GetMapping("/grades/get_grade/grade={gradeId}")
    public StudentGrades getGrade(@PathVariable("gradeId") Long gradeId){
        return gradeService.getStudentGrade(gradeId);
    }

    //Get all latest grades from class (Teacher)
    //TODO this

    //Submit single grade (Teacher)
    @PostMapping("/teacher/grades/submit_grade/student={studentId}")
    public StudentGrades submitGrade(
            @PathVariable Long studentId,
            @RequestBody GradeRegistrationRequest request,
            Authentication authentication
    ){
        return gradeService.submitGrade(studentId, request, authentication);

    }

    //submit grades for class (Teacher)
    //TODO this

    //Change grade (Teacher)
    @PutMapping("/teacher/grades/change_grade/student={studentId}&grade={gradeId}")
    public StudentGrades changeGrade(
            @PathVariable("studentId") Long studentId,
            @PathVariable("gradeId") Long gradeId,
            @RequestBody GradeRegistrationRequest request,
            Authentication authentication
    ){
        return gradeService.changeGrade(request, gradeId, authentication, studentId);
    }

    //Remove grade (Teacher)
    @DeleteMapping("/teacher/grades/delete/grade={gradeId}")
    public ResponseEntity<String> deleteGrade(
            @PathVariable("gradeId") Long gradeId
    ){
        gradeService.deleteGrade(gradeId);
        return ResponseEntity.status(HttpStatus.OK).body("Grade with id" + gradeId + " has been deleted!");
    }

}
