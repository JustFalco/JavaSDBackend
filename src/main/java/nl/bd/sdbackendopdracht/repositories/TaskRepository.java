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
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT s FROM Task s WHERE s.taskGivenByTeacher = ?1")
    Optional<Set<Task>> getTasksFromTeacher(User teacher);
}
