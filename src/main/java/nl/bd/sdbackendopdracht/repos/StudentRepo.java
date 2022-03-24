package nl.bd.sdbackendopdracht.repos;

import nl.bd.sdbackendopdracht.models.Student;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Long> {


    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);
}
