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
public class  CourseService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final UserService userService;
    private final NumValidation validation = new NumValidation();

    /**
     * De createCourse methode probeert op basis van de DTO een nieuw Course object te bouwen,
     * en deze vervolgens op te slaan in de database
     * @param request -> request DTO met data om een Course object te maken
     * @param authentication -> Standaard object om te kijken wie ingelogd is
     * @return Course object dat is aangemaakt, anders een error
     */
    public Course createCourse(CourseRegistrationRequest request, Authentication authentication) {
        if (!validation.validateId(request.teacherGivesCourseId())) {
            throw new CourseProcessExeption("Could not create course because of invalid id: " + request.teacherGivesCourseId());
        }

        User courseGivenBy;
        User courseCreator;
        Course courseToBeCreated;

        try {
            courseGivenBy = userService.getUserByUserId(request.teacherGivesCourseId());
            courseCreator = userService.getPersonalUserDetails(authentication.getName());
            if(courseCreator.getRoleEnums() == RoleEnums.STUDENT || courseGivenBy.getRoleEnums() == RoleEnums.STUDENT){
                throw new CourseProcessExeption("Could not create course because given users do not have the right permissions");
            }
            courseToBeCreated = Course.builder()
                    .courseName(request.courseName())
                    .courseDescription(request.courseDescription())
                    .belongsToSchool(courseCreator.getSchool())
                    .teacherGivesCourse(courseGivenBy)
                    .build();

            return courseRepository.save(courseToBeCreated);
        } catch (Exception e) {
            throw new CourseProcessExeption("Could not create course: " + e.getMessage());
        }
    }

    /**
     * De removeCourse methode verwijderd een Course object uit de database als deze bestaat
     * @param courseId -> id van de Course die verwijderd moet worden
     */
    public void removeCourse(Long courseId) {
        if (courseRepository.findById(courseId).isEmpty()) {
            throw new CourseNotFoundExeption("Course with id: " + courseId + " not found");
        } else {
            courseRepository.deleteById(courseId);
        }
    }

    /**
     * De changeCourse methode haalt een Course object uit de database, en wijzigd de gegevens van dit object waar nodig.
     * Daarna wordt het object weer opgeslagen in de database.
     * @param request -> request DTO met data om een Course object aan te passen
     * @param courseId -> id van de course die aangepast moet worden
     * @return het aangepaste Course object, anders een error
     */
    public Course changeCourse(CourseRegistrationRequest request, Long courseId) {
        Course courseToBeChanged;
        User teacherForCourse = null;
        try {
            courseToBeChanged = getCourse(courseId);
            if (request.teacherGivesCourseId() != null && request.teacherGivesCourseId() != 0) {
                teacherForCourse = userService.getUserByUserId(request.teacherGivesCourseId());
            }

        } catch (CourseNotFoundExeption | UserNotFoundExeption exception) {
            throw new CourseProcessExeption("Could not change course: " + exception.getMessage());
        }

        if (courseToBeChanged != null) {
            if (request.courseName() != null) {
                courseToBeChanged.setCourseName(request.courseName());
            }
            if (request.courseDescription() != null) {
                courseToBeChanged.setCourseDescription(request.courseDescription());
            }
            if (teacherForCourse != null) {
                if(teacherForCourse.getRoleEnums() == RoleEnums.STUDENT){
                    throw new CourseProcessExeption("Could not change course because teacher id refrences a student");
                }
                courseToBeChanged.setTeacherGivesCourse(teacherForCourse);
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

    /**
     * De getCourse methode zoekt naar een Course object in de database en geeft de gegevens
     * van dit object terug als het gevonden wordt
     * @param courseId -> id van de Course die opgevraagd wordt
     * @return Course object, anders een error
     */
    public Course getCourse(Long courseId) {
        Course course;
        if (courseRepository.findById(courseId).isEmpty()) {
            throw new CourseNotFoundExeption("Course with id: " + courseId + " has not been found in the database!");
        } else {
            course = courseRepository.findById(courseId).get();
        }

        return course;
    }

    /**
     * De removeStudentFromCourse methode haalt een Course object op uit de database, en loopt vervolgens
     * door elke student die de course volgt.
     * De studenten die niet verwijderd moeten worden worden in een lijst toegevoegd aan het
     * Course object en dit object wordt opgeslagen in de database
     * @param courseId -> id van de course waar een student uit gehaald moet worden
     * @param studentId -> id van de student die uit een course gehaald moet worden
     * @return Course object, anders een error
     */
    public Course removeStudentFromCourse(Long courseId, Long studentId) {
        Course course = getCourse(courseId);
        Set<User> newStudentList = new HashSet<>();
        for (User student : course.getStudentsFollowingCourse()) {
            if (student.getUserId() != studentId && student.getRoleEnums() == RoleEnums.STUDENT) {
                newStudentList.add(student);
            }
        }

        if(newStudentList.size() == course.getStudentsFollowingCourse().size()){
            throw new CourseProcessExeption("No student has been removed!");
        }

        course.setStudentsFollowingCourse(newStudentList);
        return courseRepository.save(course);
    }

    /**
     * De addStudentToCourse methode haalt een Course object uit de database,
     * voegt hier een student aan toe en slaat het Course object vervolgens weer op in de database
     * @param studentId -> id van de student die toegevoegd moet worden aan een course
     * @param courseId -> id van de course waar een student aan toegevoegd moet worden
     * @return Course object, anders een error
     */
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

    /**
     * De addMultipleStudentsToCourse methode haalt een Course object uit de database, en voegt hier vervolgens meerdere studenten aan toe.
     * Als dit lukt wordt het object weer opgeslagen in de database
     * @param usersToBeAddedIds -> Lijst van studenten ID's die toegevoegd moeten worden aan een course
     * @param courseId -> id van de course waar studenten aan toegevoegd moeten worden
     * @return Course object, anders een error
     */
    public Course addMultipleStudentsToCourse(List<Long> usersToBeAddedIds, Long courseId) {
        Course course = getCourse(courseId);
        for (Long id : usersToBeAddedIds) {
            User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundExeption("User with id: " + id + " has not been found in database!"));
            if(user.getRoleEnums() != RoleEnums.STUDENT){
                throw new CourseProcessExeption("Trying to add a non student to course!");
            }
            course.addUserToCourse(user);
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
