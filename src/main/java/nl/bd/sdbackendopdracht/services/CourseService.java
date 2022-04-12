package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.CourseRegistrationRequest;
import nl.bd.sdbackendopdracht.models.datamodels.Course;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.repositories.CourseRepository;
import nl.bd.sdbackendopdracht.repositories.UserRepository;
import nl.bd.sdbackendopdracht.security.enums.RoleEnums;
import nl.bd.sdbackendopdracht.security.exeptions.UserNotFoundExeption;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class CourseService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    //Create course
    public Course createCourse(CourseRegistrationRequest request){
        //TODO validation
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
    public void removeCourse(Long courseId){
        //TODO validation
        courseRepository.deleteById(courseId);
    }
    //Change course
    public Course changeCourse(CourseRegistrationRequest request, Long courseId){
        Course courseToBeChanged = getCourseDetails(courseId);
        courseToBeChanged.setCourseName(request.getCourseName());
        courseToBeChanged.setCourseDescription(request.getCourseDescription());
        //TODO validation
        User teacherForCourse = userRepository.getById(request.getTeacherGivesCourseId());
        courseToBeChanged.setTeacherGivesCourse(teacherForCourse);
        return courseRepository.save(courseToBeChanged);
    }

    //Get students from course
    public Set<User> getStudentsOnCourse(Long courseId){
        Course course = getCourseDetails(courseId);
        return course.getStudentsFollowingCourse();
    }

    //Get course details
    public Course getCourseDetails(Long courseId){
        //TODO validation
        return courseRepository.getById(courseId);
    }

    //Remove student from course
    public Course removeStudentFromCourse(Long courseId, Long studentId){
        Course course = getCourseDetails(courseId);
        Set<User> newStudentList = new HashSet<>();
        //TODO refactor
        for (User student : course.getStudentsFollowingCourse()){
            if(student.getUserId() != studentId){
                newStudentList.add(student);
            }
        }
        course.setStudentsFollowingCourse(newStudentList);
        return courseRepository.save(course);
    }

    //Add student to course
    public Course addStudentToCourse(Long studentId, Long courseId){
        Course course = getCourseDetails(courseId);
        User studentToBeAdded = userRepository.getById(studentId);

        if(studentToBeAdded.getRoleEnums() == RoleEnums.STUDENT){
            course.addUserToCourse(studentToBeAdded);
            return courseRepository.save(course);
        }else {
            throw new IllegalStateException("This user is not a student!");
        }
    }

    //Add multiple students to course
    public Course addMultipleStudentsToCourse(Set<User> usersToBeAdded, Long courseId){
        Course course = getCourseDetails(courseId);
        course.setStudentsFollowingCourse(usersToBeAdded);
        return courseRepository.save(course);
    }

    //Set other teacher on course
    public Course changeCourseTeacher(Long teacherUserId, Long courseId){
        //TODO validation
        User newTeacherForCourse = userRepository.getById(teacherUserId);
        Course course = getCourseDetails(courseId);
        course.setTeacherGivesCourse(newTeacherForCourse);
        return courseRepository.save(course);
    }
}
