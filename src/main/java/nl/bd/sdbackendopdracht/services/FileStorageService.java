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

    /**
     * De store functie maakt een taskFile object aan, en probeerd deze op te slaan in de database
     * @param file -> bestand dat opgeslagen moet worden (pdf, txt, png, etc.)
     * @param task -> de taak waar een bestand aan toegevoegd moet worden
     * @return TaskFile object van het opgeslagen bestand, anders een error
     */
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

    /**
     * De getFile methode haalt een bestand van een taak op uit de database en geeft deze zo terug op een manier
     * waardoor het bestand gedownload kan worden door de gebruiker.
     * @param id -> id van het bestand dat opgehaald moet worden uit de database
     * @return TaskFile object, ander een error
     */
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

    /**
     * Methode om alle files van één specifieke taak terug te sturen naar de gebruiker
     * @param taskId -> id van de taak waar de bestanden van teruggestuurd moeten worden
     * @return Lijst van bestanden, ander een error
     */
    public Stream<TaskFile> getAllFiles(Long taskId) {
        Task task = tasksService.getTask(taskId);
        Set<TaskFile> taskFiles = taskFileRepository.getFilesFromTask(task).orElseThrow(() -> new RuntimeException("Files not found!"));
        return taskFiles.stream();
    }
}
