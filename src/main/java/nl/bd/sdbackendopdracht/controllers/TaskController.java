package nl.bd.sdbackendopdracht.controllers;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.Task;
import nl.bd.sdbackendopdracht.models.datamodels.TaskFile;
import nl.bd.sdbackendopdracht.models.requestmodels.TaskRegistrationRequest;
import nl.bd.sdbackendopdracht.models.responsemodels.ResponseFile;
import nl.bd.sdbackendopdracht.services.FileStorageService;
import nl.bd.sdbackendopdracht.services.TasksService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class TaskController {

    public final TasksService tasksService;
    public final FileStorageService storageService;

    //Create task for course(Teacher)
    @PostMapping("/teacher/task/create_task/course={courseId}")
    public Task createTaskForCourse(
            @RequestBody TaskRegistrationRequest taskRegistrationRequest,
            Authentication authentication,
            @PathVariable("courseId") Long courseId
            ){
        return tasksService.createTaskForCourse(taskRegistrationRequest, authentication, courseId);
    }

    //Create task (Teacher)
    @PostMapping("/teacher/task/create_task")
    public Task createTask(
            @RequestBody TaskRegistrationRequest taskRegistrationRequest,
            Authentication authentication
    ){
        Task task = tasksService.createTask(taskRegistrationRequest, authentication.getName());
        return task;
    }

    //Add file to task(Teacher)
    @PostMapping("/teacher/task/add_file/task={taskId}")
    public Task addFileToTask(
            @PathVariable("taskId") Long taskId,
            @RequestParam("file") MultipartFile file
    ){
        Task task = tasksService.getTask(taskId);
        storageService.store(file, task);
        return task;
    }

    /**
     * Veel van deze code komt van https://www.bezkoder.com/spring-boot-upload-file-database/
     * @param fileId
     * @return
     */
    //Get file by id (all)
    @GetMapping("/task/get_taskfiles/file={fileId}")
    public ResponseEntity<byte[]> getFile(@PathVariable("fileId") String fileId) {
        TaskFile fileDB = storageService.getFile(fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }

    /**
     * Veel van deze code komt van https://www.bezkoder.com/spring-boot-upload-file-database/
     * @param taskId
     * @return
     */
    //Get files from task (all)
    @GetMapping("/task/get_taskfiles/task={taskId}")
    public  ResponseEntity<List<ResponseFile>> getAllTaskfilesFromTask(
            @PathVariable("taskId") Long taskId
    ){
        List<ResponseFile> files = storageService.getAllFiles(taskId).map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/v1/task/get_taskfiles/file=")
                    .path(dbFile.getFileId())
                    .toUriString();
            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

//    //Get files from task (all)
//    @GetMapping("/task/get_taskfiles/task={taskId}")
//    public Set<ResponseFile> getAllTaskfilesFromTask(
//            @PathVariable("taskId") Long taskId
//    ){
//        Set<ResponseFile> responseFiles = new HashSet<>();
//        for (TaskFile file : storageService.getAllFiles(taskId)){
//
//            String fileDownloadUri = ServletUriComponentsBuilder
//                    .fromCurrentContextPath()
//                    .path("/files/")
//                    //TODO zou nog wel eens fout kunnen gaan
//                    .path(file.getName())
//                    .toUriString();
//
//            ResponseFile responseFile = ResponseFile.builder()
//                    .name(file.getName())
//                    .downloadUri(fileDownloadUri)
//                    .type(file.getType())
//                    .size(file.getData().length)
//                    .build();
//        }
//        return responseFiles;
//    }

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
