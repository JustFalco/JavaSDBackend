package nl.bd.sdbackendopdracht.controllers;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.TaskRegistrationRequest;
import nl.bd.sdbackendopdracht.models.datamodels.Task;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.services.StudentService;
import nl.bd.sdbackendopdracht.services.TasksService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class ApiController {

    private final TasksService tasksService;

//    @GetMapping
//    public Set<Task> getAllStudentTasks(){
//        return tasksService.getAllTasksFromStudent("falco.wolkorte2910@gmail.com");
//    }

    @GetMapping(value = "/allTasks")
    public List<Task> getAllActiveTasks(){
        return tasksService.getAllTasks();
    }

    @PostMapping(value = "/give_task/{userId}")
    public Task giveTaskToStudent(
            @RequestBody TaskRegistrationRequest request,
            @PathVariable Long userId
    ){
        return tasksService.giveTaskToUser(request, userId);
    }

    private final StudentService studentService;

    @GetMapping(value = "/get_all_students")
    public List<User> getStudents(){
        return studentService.getStudents();
    }

    @GetMapping(value = "/get_personal_details")
    public User personalDetails(Authentication authentication){
        return studentService.getPersonalUserDetails(authentication.getName());
    }
}
