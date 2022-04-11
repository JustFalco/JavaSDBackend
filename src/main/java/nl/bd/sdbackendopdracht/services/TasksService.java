package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.TaskRegistrationRequest;
import nl.bd.sdbackendopdracht.models.datamodels.Course;
import nl.bd.sdbackendopdracht.models.datamodels.Task;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.repositories.CourseRepository;
import nl.bd.sdbackendopdracht.repositories.TaskRepository;
import nl.bd.sdbackendopdracht.repositories.UserRepository;
import nl.bd.sdbackendopdracht.security.exeptions.TaskNotFoundExeption;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class TasksService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CourseRepository courseRepository;

    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User does not exists"));
    }

    public Task createTask(TaskRegistrationRequest request, Long userId, String currentUserMail){
        //TODO fix builder
        User personalUserDetails = userService.getPersonalUserDetails(currentUserMail);
        Task task = Task.builder()
                .taskName(request.getTaskName())
                .taksDescription(request.getTaskDescription())
                .taksDeadline(request.getTaskDeadline())
                //TODO klok toevoegen
                .timeOfTaskPublication(LocalDateTime.now())
                .taskFinished(false)
                .taskGivenByTeacher(personalUserDetails)
                .build();

        return taskRepository.save(task);
    }

    public Task giveTaskToUser(Long taskId, Long userId){
        //TODO validation
        Task taskFromDatabase = taskRepository.getById(taskId);
        //TODO validation
        User userToBeAddedToTask = userRepository.getById(userId);

        taskFromDatabase.addUser(userToBeAddedToTask);
        return taskRepository.save(taskFromDatabase);
    }

    public void giveTaskToCourseClass(Long courseId, Long taskId){
        //TODO validation
        Course couseToGiveTaskTo = courseRepository.getById(courseId);

        for(User user : couseToGiveTaskTo.getStudentsFollowingCourse()){
            giveTaskToUser(taskId, user.getUserId());
        }
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id){
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundExeption("Task with id: " + id + " does not exists!"));
    }

    public Set<Task> getAllTasksFromStudent(Long userId){
        //TODO validation
        User user = userRepository.getById(userId);
        return user.getUserHasTasks();
    }

    public Task changeTask(Long taskId, TaskRegistrationRequest request){
        //TODO validation
        Task taskToChange = taskRepository.getById(taskId);
        taskToChange.setTaskName(request.getTaskName());
        taskToChange.setTaksDescription(request.getTaskDescription());
        taskToChange.setTaksDeadline(request.getTaskDeadline());

        return taskRepository.save(taskToChange);
    }
    
    public void deleteTask(Long taskId){
        taskRepository.deleteById(taskId);
    }

    //TODO method for removing student from task
}
