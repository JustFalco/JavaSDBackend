package nl.bd.sdbackendopdracht.controllers;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.Course;
import nl.bd.sdbackendopdracht.models.requestmodels.CourseRegistrationRequest;
import nl.bd.sdbackendopdracht.services.CourseService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;

    //Course aanmaken (Administrator)
    @PostMapping("/course/create")
    public Course createCourse(@RequestBody CourseRegistrationRequest request){
        return courseService.createCourse(request);
    }

    //Course veranderen (Administrator)
    @PutMapping("/course/change/course={courseId}")
    public Course changeCourse(
            @RequestBody CourseRegistrationRequest request,
            @PathVariable("courseId") Long courseId
    ){
        return courseService.changeCourse(request, courseId);
    }

    //Student toevoegen aan course (Administrator)

    //Student verwijderen uit course (Administrator)

    //Meerdere studenten toevoegen aan course (Administrator)

    //Course verwijderen (Administrator)

    //Course gegevens opvragen (All)


}
