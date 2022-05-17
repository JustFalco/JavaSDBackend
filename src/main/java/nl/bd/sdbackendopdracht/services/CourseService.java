package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.Course;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.models.requestmodels.CourseRegistrationRequest;
import nl.bd.sdbackendopdracht.repositories.CourseRepository;
import nl.bd.sdbackendopdracht.repositories.UserRepository;
import nl.bd.sdbackendopdracht.security.enums.RoleEnums;
import nl.bd.sdbackendopdracht.security.exeptions.CourseNotFoundExeption;
import nl.bd.sdbackendopdracht.security.exeptions.CourseProcessExeption;
import nl.bd.sdbackendopdracht.security.exeptions.UserNotFoundExeption;
import nl.bd.sdbackendopdracht.security.validation.NumValidation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class CourseService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final UserService userService;
    private final NumValidation validation = new NumValidation();

    //Create course
    public Course createCourse(CourseRegistrationRequest request, Authentication authentication) {
        if (!validation.validateId(request.getTeacherGivesCourseId())) {
            throw new CourseProcessExeption("Could not create course because of invalid id: " + request.getTeacherGivesCourseId());
        }

        User courseGivenBy = null;
        User courseCreator = null;
        Course courseToBeCreated = null;

        try {
            courseGivenBy = userService.getUserByUserId(request.getTeacherGivesCourseId());
            courseCreator = userService.getPersonalUserDetails(authentication.getName());
            courseToBeCreated = Course.builder()
                    .courseName(request.getCourseName())
                    .courseDescription(request.getCourseDescription())
                    .belongsToSchool(courseCreator.getSchool())
                    .teacherGivesCourse(courseGivenBy)
                    .build();

            return courseRepository.save(courseToBeCreated);
        } catch (Exception e) {
            throw new CourseProcessExeption("Could not create course: " + e.getMessage());
        }
    }

    //Remove course
    public void removeCourse(Long courseId) {
        if (courseRepository.findById(courseId).isEmpty()) {
            throw new CourseNotFoundExeption("Course with id: " + courseId + " not found");
        } else {
            courseRepository.deleteById(courseId);
        }
    }

    //Change course
    public Course changeCourse(CourseRegistrationRequest request, Long courseId) {
        Course courseToBeChanged = null;
        User teacherForCourse = null;
        try {
            courseToBeChanged = getCourse(courseId);
            if (request.getTeacherGivesCourseId() != null && request.getTeacherGivesCourseId() != 0) {
                teacherForCourse = userService.getUserByUserId(request.getTeacherGivesCourseId());
            }

        } catch (CourseNotFoundExeption | UserNotFoundExeption exception) {
            throw new RuntimeException("Could not change course: " + exception.getMessage());
        } finally {
            if (courseToBeChanged != null) {
                if (request.getCourseName() != null) {
                    courseToBeChanged.setCourseName(request.getCourseName());
                }
                if (request.getCourseDescription() != null) {
                    courseToBeChanged.setCourseDescription(request.getCourseDescription());
                }
                if (teacherForCourse != null) {
                    courseToBeChanged.setTeacherGivesCourse(teacherForCourse);
                }
            }
        }

        assert courseToBeChanged != null;
        return courseRepository.save(courseToBeChanged);
    }

    //Get students from course
    public Set<User> getStudentsOnCourse(Long courseId) {
        Course course = getCourse(courseId);
        return course.getStudentsFollowingCourse();
    }

    //Get course details
    public Course getCourse(Long courseId) {
        Course course = null;
        if (courseRepository.findById(courseId).isEmpty()) {
            throw new CourseNotFoundExeption("Course with id: " + courseId + " has not been found in the database!");
        } else {
            course = courseRepository.findById(courseId).get();
        }

        return course;
    }

    //Remove student from course
    public Course removeStudentFromCourse(Long courseId, Long studentId) {
        Course course = getCourse(courseId);
        Set<User> newStudentList = new HashSet<>();
        for (User student : course.getStudentsFollowingCourse()) {
            if (!Objects.equals(student.getUserId(), studentId)) {
                newStudentList.add(student);
            }
        }
        course.setStudentsFollowingCourse(newStudentList);
        return courseRepository.save(course);
    }

    //Add student to course
    public Course addStudentToCourse(Long studentId, Long courseId) {
        Course course = getCourse(courseId);
        User studentToBeAdded = userService.getUserByUserId(studentId);

        if (studentToBeAdded.getRoleEnums() == RoleEnums.STUDENT) {
            course.addUserToCourse(studentToBeAdded);
        } else {
            throw new IllegalStateException("This user is not a student!");
        }

        return courseRepository.save(course);
    }

    //Add multiple students to course
    public Course addMultipleStudentsToCourse(List<Long> usersToBeAddedIds, Long courseId) {
        Course course = getCourse(courseId);
        for (Long id : usersToBeAddedIds) {
            course.addUserToCourse(userRepository.findById(id).get());
        }
        return courseRepository.save(course);
    }

    //Set other teacher on course
    public Course changeCourseTeacher(Long teacherUserId, Long courseId) {
        User newTeacherForCourse = userService.getUserByUserId(teacherUserId);
        Course course = getCourse(courseId);
        course.setTeacherGivesCourse(newTeacherForCourse);
        return courseRepository.save(course);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User does not exists"));
    }
}
