package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.Course;
import nl.bd.sdbackendopdracht.models.datamodels.Task;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.models.requestmodels.TaskRegistrationRequest;
import nl.bd.sdbackendopdracht.repositories.CourseRepository;
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
    private final CourseRepository courseRepository;

    private final CourseService courseService;
    private final UserService userService;

    private final NumValidation validation = new NumValidation();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User does not exists"));
    }

    public Task createTask(TaskRegistrationRequest request, String currentUserMail) {
        User personalUserDetails = userService.getPersonalUserDetails(currentUserMail);
        Task task = Task.builder()
                .taskName(request.getTaskName())
                .taksDescription(request.getTaskDescription())
                .taksDeadline(request.getTaskDeadline())
                .timeOfTaskPublication(LocalDateTime.now(Clock.systemDefaultZone()))
                .taskFinished(false)
                .taskGivenByTeacher(personalUserDetails)
                .build();

        return taskRepository.save(task);
    }

    public Task giveTaskToStudent(Long taskId, Long userId) {
        Task taskFromDatabase = getTask(taskId);
        User userToBeAddedToTask = userService.getUserByUserId(userId);

        taskFromDatabase.addUser(userToBeAddedToTask);
        return taskRepository.save(taskFromDatabase);
    }

    public void giveTaskToCourseClass(Long courseId, Long taskId) {
        Course couseToGiveTaskTo = courseService.getCourse(courseId);

        for (User user : couseToGiveTaskTo.getStudentsFollowingCourse()) {
            giveTaskToStudent(taskId, user.getUserId());
        }
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Set<Task> getAllTasksFromStudent(Long userId) {
        User user = userService.getUserByUserId(userId);

        return user.getUserHasTasks();
    }


    public Task changeTask(Long taskId, TaskRegistrationRequest request) {
        Task taskToChange = getTask(taskId);
        if(request.getTaskName() != null && request.getTaskName() != ""){
            taskToChange.setTaskName(request.getTaskName());
        }
        if(request.getTaskDescription() != null && request.getTaskDescription() != ""){
            taskToChange.setTaksDescription(request.getTaskDescription());
        }
        if(request.getTaskDeadline() != null){
            taskToChange.setTaksDeadline(request.getTaskDeadline());
        }

        return taskRepository.save(taskToChange);
    }

    public void deleteTask(Long taskId) {
        boolean empty = taskRepository.findById(taskId).isEmpty();
        if(empty){
            throw new TaskNotFoundExeption("Task with id: " + taskId + " does not exists!");
        }else{
            taskRepository.deleteById(taskId);
        }

    }

    //Method for removing student from task
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

    //Get one task
    public Task getTask(Long taskId) {
        Task task;
        if(!validation.validateId(taskId)){
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

    public Task createTaskForCourse(TaskRegistrationRequest request, Authentication authentication, Long courseId) {
        Task task = createTask(request, authentication.getName());
        taskRepository.save(task);

        giveTaskToCourseClass(courseId, task.getTaskId());
        return getTask(task.getTaskId());
    }

    public Set<Task> getTasksFromTeacher(Long teacherId){
        User teacher = userService.getUserByUserId(teacherId);
        return taskRepository.getTasksFromTeacher(teacher).orElseThrow(
                () -> new TaskNotFoundExeption("No tasks found belonging to teacher: " + teacher.getFirstName())
        );
    }
}
