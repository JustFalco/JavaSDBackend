package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.Task;
import nl.bd.sdbackendopdracht.models.datamodels.TaskFile;
import nl.bd.sdbackendopdracht.repositories.TaskFileRepository;
import nl.bd.sdbackendopdracht.security.exeptions.FileProcessorExeption;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class FileStorageService {

    /**
     * Code van <a href="https://www.bezkoder.com/spring-boot-upload-file-database/">https://www.bezkoder.com/spring-boot-upload-file-database/</a>
     */

    private TaskFileRepository taskFileRepository;
    private TasksService tasksService;

    public TaskFile store(MultipartFile file, Task task) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        TaskFile taskFile;
        try {
            if (task == null) {
                taskFile = TaskFile.builder()
                        .name(fileName)
                        .type(file.getContentType())
                        .data(file.getBytes())
                        .build();
            } else {
                taskFile = TaskFile.builder()
                        .name(fileName)
                        .type(file.getContentType())
                        .data(file.getBytes())
                        .fileBelongsToTask(task)
                        .build();
            }

        } catch (IOException exception) {
            throw new FileProcessorExeption("file could not be stored: " + exception.getMessage());
        }

        if (taskFile == null) {
            throw new FileProcessorExeption("file could not be stored!");
        }

        return taskFileRepository.save(taskFile);
    }

    public TaskFile getFile(String id) {
        TaskFile file;
        boolean empty = taskFileRepository.findById(id).isEmpty();
        if (empty) {
            throw new RuntimeException("File with id: " + id + " has not been found!");
        } else {
            file = taskFileRepository.findById(id).get();
        }
        return file;
    }

    public Stream<TaskFile> getAllFiles(Long taskId) {
        Task task = tasksService.getTask(taskId);
        Set<TaskFile> taskFiles = taskFileRepository.getFilesFromTask(task).orElseThrow(() -> new RuntimeException("Files not found!"));
        return taskFiles.stream();
    }
}
