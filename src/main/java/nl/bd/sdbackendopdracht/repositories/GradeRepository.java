package nl.bd.sdbackendopdracht.repositories;

import nl.bd.sdbackendopdracht.models.datamodels.StudentGrades;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface GradeRepository extends JpaRepository<StudentGrades, Long> {

    @Query("SELECT s FROM StudentGrades s WHERE s.studentMarkId = ?1")
    Optional<List<StudentGrades>> findAllById(Long gradeId);

}
