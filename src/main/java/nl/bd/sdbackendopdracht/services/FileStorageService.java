package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.Task;
import nl.bd.sdbackendopdracht.models.datamodels.TaskFile;
import nl.bd.sdbackendopdracht.repositories.TaskFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class FileStorageService {

    /**
     * Code van https://www.bezkoder.com/spring-boot-upload-file-database/
     */

    private TaskFileRepository taskFileRepository;
    private TasksService tasksService;
    public TaskFile store(MultipartFile file, Task task){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        TaskFile taskFile = null;
        try{
            if(task == null){
                taskFile = TaskFile.builder()
                        .name(fileName)
                        .type(file.getContentType())
                        .data(file.getBytes())
                        .build();
            }else{
                taskFile = TaskFile.builder()
                        .name(fileName)
                        .type(file.getContentType())
                        .data(file.getBytes())
                        .fileBelongsToTask(task)
                        .build();
            }

        }catch (IOException exception){
            //TODO fix this shit?
            taskFile = null;
        }

        if(taskFile != null){
            return taskFileRepository.save(taskFile);
        }else {
            throw new RuntimeException("Task could not be created!");
        }

    }

    public TaskFile getFile(String id){
        TaskFile file = null;
        boolean empty = taskFileRepository.findById(id).isEmpty();
        if(empty){
            throw new RuntimeException("File with id: " + id + " has not been found!");
        }else{
            file = taskFileRepository.findById(id).get();
        }
        return file;
    }

    public Stream<TaskFile> getAllFiles(Long taskId){
        Task task = tasksService.getTask(taskId);
        Set<TaskFile> taskFiles = taskFileRepository.getFilesFromTask(task).orElseThrow(() -> new RuntimeException("Files not found!"));
        return taskFiles.stream();
    }
}
