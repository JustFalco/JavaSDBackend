package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.TaskRegistrationRequest;
import nl.bd.sdbackendopdracht.models.datamodels.Task;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.repositories.TaskRepository;
import nl.bd.sdbackendopdracht.repositories.UserRepository;
import nl.bd.sdbackendopdracht.security.exeptions.TaskNotFoundExeption;
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


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User does not exists"));
    }

    public Task giveTaskToUser(TaskRegistrationRequest request, Long userId){
        User user = userRepository.getById(userId);
        Task task = Task.builder()
                .taskName(request.getTaskName())
                .taksDescription(request.getTaskDescription())
                .taksDeadline(LocalDateTime.of(2022, Month.OCTOBER, 29,23,59,30))
                .timeOfTaskPublication(LocalDateTime.of(2022, Month.APRIL, 29,23,59,30))
                .build();
        task.addUser(user);


        return taskRepository.save(task);
    }

    public Set<Task> giveTaskToCourseClass(){
        return null;
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id){
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundExeption("Task with id: " + id + " does not exists!"));
    }

    public Set<Task> getAllTasksFromStudent(String email){
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("no"));
        return user.getUserHasTasks();
    }
}
