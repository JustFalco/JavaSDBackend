package nl.bd.sdbackendopdracht.repos;

import nl.bd.sdbackendopdracht.models.Student;
import nl.bd.sdbackendopdracht.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepo extends JpaRepository<Teacher, Long> {
}
