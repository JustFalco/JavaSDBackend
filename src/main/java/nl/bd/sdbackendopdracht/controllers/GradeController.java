package nl.bd.sdbackendopdracht.controllers;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.StudentGrades;
import nl.bd.sdbackendopdracht.models.requestmodels.GradeRegistrationRequest;
import nl.bd.sdbackendopdracht.services.GradeService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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

    //Get all grades (Student)
    @GetMapping("/student/grades/get_grade/overview/student={userId}")
    public List<StudentGrades> getGradeOverview(@PathVariable("userId") Long userId){
        return gradeService.getGradesFromStudent(userId);
    }

    //Get one grade (Student)
    @GetMapping("/student/grades/get_grade/{gradeId}")
    public StudentGrades getGrade(@PathVariable Long gradeId){
        //TODO validate Long
        return gradeService.getStudentGrade(gradeId);
    }

    //Get one grade (Teacher)
    @GetMapping("/teacher/grades/get_grade/grade={gradeId}")
    public StudentGrades getGradeTeacher(@PathVariable Long gradeId){
        //TODO validate Long
        return getGrade(gradeId);
    }

    //Get all grades from student (Teacher)
    @GetMapping("/teacher/grades/get_grade/student={studentId}")
    public List<StudentGrades> getStudentGrades(
            @PathVariable("studentId") Long studentId
    ){
        return gradeService.getGradesFromStudent(studentId);
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
        return gradeService.changeGrade(request, gradeId, authentication);
    }

    //Remove grade (Teacher)
    @DeleteMapping("/teacher/grades/delete/grade={gradeId}")
    public void deleteGrade(
            @PathVariable("gradeId") Long gradeId
    ){
        gradeService.deleteGrade(gradeId);
    }

}
