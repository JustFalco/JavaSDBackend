package nl.bd.sdbackendopdracht.controllers;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.Course;
import nl.bd.sdbackendopdracht.models.requestmodels.CourseRegistrationRequest;
import nl.bd.sdbackendopdracht.services.CourseService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;

    //Course aanmaken (Administrator)
    @PostMapping("/administrator/course/create")
    public Course createCourse(@RequestBody CourseRegistrationRequest request, Authentication authentication) {
        return courseService.createCourse(request, authentication);
    }

    //Course veranderen (Administrator)
    @PutMapping("/administrator/course/change/course={courseId}")
    public Course changeCourse(
            @RequestBody CourseRegistrationRequest request,
            @PathVariable("courseId") Long courseId
    ) {
        return courseService.changeCourse(request, courseId);
    }

    //Student toevoegen aan course (Administrator)
    @PostMapping("/administrator/course/add_student/student={studentId}&course={courseId}")
    public Course addStudentToCourse(
            @PathVariable("studentId") Long studentId,
            @PathVariable("courseId") Long courseId
    ) {
        return courseService.addStudentToCourse(studentId, courseId);
    }

    //Student verwijderen uit course (Administrator)
    @DeleteMapping("/administrator/course/delete_student/student={studentId}&course={courseId}")
    public String removeStudent(
            @PathVariable("studentId") Long studentId,
            @PathVariable("courseId") Long courseId
    ) {
        courseService.removeStudentFromCourse(courseId, studentId);
        return "Student with id: " + studentId + " has succesfully been removed from course with id: " + courseId + "!";
    }

    //Meerdere studenten toevoegen aan course (Administrator)
    @PostMapping("/administrator/course/add_students/course={courseId}")
    public Course addMultipleStudentsToCourse(
            @PathVariable("courseId") Long courseId,
            @RequestBody List<Long> studentIds
    ) {
        return courseService.addMultipleStudentsToCourse(studentIds, courseId);
    }

    //Course verwijderen (Administrator)
    @DeleteMapping("/administrator/course/remove/course={courseId}")
    public String removeCourse(
            @PathVariable("courseId") Long courseId
    ) {
        courseService.removeCourse(courseId);
        return "Course with id: " + courseId + " has succesfully been removed!";
    }

    //Course gegevens opvragen (All)
    @GetMapping("/user/course/get_details/course={courseId}")
    public Course getCourseDetails(
            @PathVariable("courseId") Long courseId
    ) {
        return courseService.getCourse(courseId);
    }

}
