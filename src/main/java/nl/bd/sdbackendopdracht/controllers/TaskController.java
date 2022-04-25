package nl.bd.sdbackendopdracht.controllers;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.Task;
import nl.bd.sdbackendopdracht.models.requestmodels.TaskRegistrationRequest;
import nl.bd.sdbackendopdracht.services.TasksService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class TaskController {

    public final TasksService tasksService;

    //Create task (Teacher)
    @PostMapping("/teacher/task/create_task/course={courseId}")
    public Task createTaskForCourse(
            @RequestBody TaskRegistrationRequest taskRegistrationRequest,
            Authentication authentication,
            @PathVariable("courseId") Long courseId
    ){
        return tasksService.createTaskForCourse(taskRegistrationRequest, authentication, courseId);
    }

    //Change task (Teacher)
    //TODO alles wordt naar null geschreven
    @PutMapping("/teacher/task/change/task={taskId}")
    public Task changeTask(
            TaskRegistrationRequest registrationRequest,
            @PathVariable("taskId") Long taskId
    ){
        return tasksService.changeTask(taskId, registrationRequest);
    }

    //Get task details (all)
    @GetMapping("/task/get_taskdetails/task={taskId}")
    public Task getTaskDetails(
            @PathVariable("taskId") Long taskId
    ){
        return tasksService.getTask(taskId);
    }

    //Get all tasks from student (all)
    @GetMapping("/task/get_tasks_from_student/student={userId}")
    public Set<Task> getTasksFromStudent(
            @PathVariable("userId") Long userId
    ){
        return tasksService.getAllTasksFromStudent(userId);
    }

    //Add user to task (Teacher)
    @PutMapping("/teacher/task/add_student/student={userId}&task={taskId}")
    public Task addStudentToTask(
            @PathVariable("userId") Long userId,
            @PathVariable("taskId") Long taskId
    ){
        return tasksService.giveTaskToStudent(taskId, userId);
    }

    //Remove user from task (Teacher)
    @DeleteMapping("/teacher/task/delete/student={userId}&task={taskId}")
    public void deleteTask(
            @PathVariable("userId") Long userId,
            @PathVariable("taskId") Long taskId
    ){
        tasksService.removeStudentFromTask(taskId, userId);
    }

    //Remove task (Teacher)
    @DeleteMapping("/teacher/task/delete/task={taskId}")
    public void deleteTask(
            @PathVariable("taskId") Long taskId
    ){
        tasksService.deleteTask(taskId);
    }

    //Finish task (Student)?
}
