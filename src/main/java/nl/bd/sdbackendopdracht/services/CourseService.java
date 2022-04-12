package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.CourseRegistrationRequest;
import nl.bd.sdbackendopdracht.models.datamodels.Course;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.repositories.CourseRepository;
import nl.bd.sdbackendopdracht.repositories.UserRepository;
import nl.bd.sdbackendopdracht.security.exeptions.UserNotFoundExeption;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    //Create course
    public Course createCourse(CourseRegistrationRequest request){
        User courseGivenBy = userRepository.getById(request.getTeacherGivesCourseId());

        Course courseToBeCreated = Course.builder()
                .courseName(request.getCourseName())
                .courseDescription(request.getCourseDescription())
                //TODO misschien hier nog even naar kijken
                .belongsToSchool(courseGivenBy.getSchool())
                .teacherGivesCourse(courseGivenBy)
                .build();

        return courseRepository.save(courseToBeCreated);
    }

    //Remove course

    //Change course

    //Get students from course

    //Get course details

    //Remove student from course

    //Add student to course

    //Add multiple students to course
}
