package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.Course;
import nl.bd.sdbackendopdracht.models.datamodels.Task;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.models.requestmodels.TaskRegistrationRequest;
import nl.bd.sdbackendopdracht.repositories.TaskRepository;
import nl.bd.sdbackendopdracht.repositories.UserRepository;
import nl.bd.sdbackendopdracht.security.exeptions.TaskNotFoundExeption;
import nl.bd.sdbackendopdracht.security.validation.NumValidation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class TasksService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    private final CourseService courseService;
    private final UserService userService;

    private final NumValidation validation = new NumValidation();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User does not exists"));
    }

    /**
     * De method createTask maakt een taak aan op basis van de request gegevens, en slaat deze taak op in de database.
     * @param request -> DTO met gegevens voor het registreren van een taak
     * @param currentUserMail -> Email van de ingelogde gebruiker
     * @return Task object als deze succesvol is aangemaakt
     */
    public Task createTask(TaskRegistrationRequest request, String currentUserMail) {
        //TODO meer security
        User personalUserDetails = userService.getPersonalUserDetails(currentUserMail);
        Task task = Task.builder()
                .taskName(request.taskName())
                .taksDescription(request.taskDescription())
                .taksDeadline(request.taskDeadline())
                .timeOfTaskPublication(LocalDateTime.now(Clock.systemDefaultZone()))
                .taskFinished(false)
                .taskGivenByTeacher(personalUserDetails)
                .build();

        return taskRepository.save(task);
    }

    /**
     * De giveTaskToStudent haalt een taak op uit de database.
     * Vervolgens wordt er een student opgehaald uit de database en wordt deze toegevoegd aan de taak.
     * Hierna wordt de taak weer opgeslagen in de database en wordt het taak-object teruggegeven.
     * @param taskId -> id van de taak waar een student aan toegevoegd moet worden
     * @param userId -> id van de student die toegevoegd moet worden aan een taak
     * @return Task object waar een student aan is toegevoegd, anders een error
     */
    public Task giveTaskToStudent(Long taskId, Long userId) {
        Task taskFromDatabase = getTask(taskId);
        User userToBeAddedToTask = userService.getUserByUserId(userId);

        taskFromDatabase.addUser(userToBeAddedToTask);
        return taskRepository.save(taskFromDatabase);
    }

    /**
     * De methode giveTaskToCourseClass haalt een course uit de database, en loopt vervolgens door alle studenten die de cursus volgen.
     * Deze studenten worden daarna toegevoegd aan een taak.
     * @param courseId -> id van de course waar de studenten van opgevraagd moeten worden
     * @param taskId -> id van de taak die toegewezen moet worden aan de studenten
     */
    public void giveTaskToCourseClass(Long courseId, Long taskId) {
        Course couseToGiveTaskTo = courseService.getCourse(courseId);

        for (User user : couseToGiveTaskTo.getStudentsFollowingCourse()) {
            giveTaskToStudent(taskId, user.getUserId());
        }
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Method roept een method aan in het User object die alle taken terug geeft
     * @param userId -> id van student waar alle taken van opgevraagd moeten worden
     * @return Lijst met Task objecten
     */
    public Set<Task> getAllTasksFromStudent(Long userId) {
        User user = userService.getUserByUserId(userId);

        return user.getUserHasTasks();
    }


    /**
     * De methode changeTask haalt een bestaande taak op uit de database, en veranderd hier de gegevens van waar nodig.
     * Vervolgens wordt de taak weer opgeslagen in de database
     * @param taskId -> id van de taak die veranderd moet worden
     * @param request -> DTO met gegevens voor het veranderen van de taak
     * @return Task object dat veranderd is
     */
    public Task changeTask(Long taskId, TaskRegistrationRequest request) {
        Task taskToChange = getTask(taskId);
        if (request.taskName() != null && !request.taskName().equals("")) {
            taskToChange.setTaskName(request.taskName());
        }
        if (request.taskDescription() != null && !request.taskDescription().equals("")) {
            taskToChange.setTaksDescription(request.taskDescription());
        }
        if (request.taskDeadline() != null) {
            taskToChange.setTaksDeadline(request.taskDeadline());
        }

        return taskRepository.save(taskToChange);
    }

    /**
     * De deleteTask methode kijkt of een taak in de database bestaat, en verwijderd deze als dit het geval is
     * @param taskId -> id van de taak die verwijderd moet worden
     */
    public void deleteTask(Long taskId) {
        boolean empty = taskRepository.findById(taskId).isEmpty();
        if (empty) {
            throw new TaskNotFoundExeption("Task with id: " + taskId + " does not exists!");
        } else {
            taskRepository.deleteById(taskId);
        }

    }

    /**
     * De removeStudentFromTask methode haalt een taak op uit de database.
     * Vervolgens wordt er over alle gebruikers in de taak heen geloopt en elke gebruiker waarbij de userId niet overeenkomt,
     * wordt in een nieuwe lijst gezet.
     * Deze lijst wordt opgeslagen in de taak en de taak wordt vervolgens weer opgeslagen in de database.
     * @param userId -> id van de gebruiker die verwijderd moet worden uit een taak
     * @param taskId -> id van de taak waar een gebruiker uit verwijderd moet worden
     * @return Task object als het verwijderen succesvol was, anders een error
     */
    public Task removeStudentFromTask(Long userId, Long taskId) {
        Task task = getTask(taskId);
        Set<User> newUserList = new HashSet<>();
        for (User user : task.getTaskHasUsers()) {
            if (!Objects.equals(user.getUserId(), userId)) {
                newUserList.add(user);
            }
        }

        task.setTaskHasUsers(newUserList);

        return taskRepository.save(task);
    }

    /**
     * De getTask methode kijkt of een taak id in de database staat, en haalt vervolgens het taak object uit de database.
     * @param taskId -> id van de taak die uit de database gehaald moet worden
     * @return Task object, anders een error
     */
    public Task getTask(Long taskId) {
        Task task;
        if (!validation.validateId(taskId)) {
            throw new NumberFormatException("Id: " + taskId + " is an illegal number");
        }

        boolean taskDoesntExists = taskRepository.findById(taskId).isEmpty();

        if (taskDoesntExists) {
            throw new TaskNotFoundExeption("Task with id: " + taskId + " has not been found in the database!");
        } else {
            task = taskRepository.findById(taskId).get();
        }

        return task;
    }

    /**
     * De createTaskForCourse is hetzelfde als de createTask methode, met het kleine verschil dat deze methode
     * een Course toewijst aan een taak.
     * @param request -> DTO met gegevens voor het registreren van een taak
     * @param authentication -> standaard authentication object om de ingelogde gebruiker te bepalen
     * @param courseId -> id van de course die aan de taak toegevoegd moet worden
     * @return Task object wat gecreëerd is, anders een error
     */
    public Task createTaskForCourse(TaskRegistrationRequest request, Authentication authentication, Long courseId) {
        Task task = createTask(request, authentication.getName());
        taskRepository.save(task);

        giveTaskToCourseClass(courseId, task.getTaskId());
        return getTask(task.getTaskId());
    }

    public Set<Task> getTasksFromTeacher(Long teacherId) {
        User teacher = userService.getUserByUserId(teacherId);
        return taskRepository.getTasksFromTeacher(teacher).orElseThrow(
                () -> new TaskNotFoundExeption("No tasks found belonging to teacher: " + teacher.getFirstName())
        );
    }
}
