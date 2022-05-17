package nl.bd.sdbackendopdracht.repositories;

import nl.bd.sdbackendopdracht.models.datamodels.Task;
import nl.bd.sdbackendopdracht.models.datamodels.TaskFile;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TaskFileRepository extends JpaRepository<TaskFile, String> {
    @Query("SELECT s FROM TaskFile s WHERE s.fileBelongsToTask = ?1")
    Optional<Set<TaskFile>> getFilesFromTask(Task task);

}
