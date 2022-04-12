package nl.bd.sdbackendopdracht.repositories;

import nl.bd.sdbackendopdracht.models.datamodels.StudentGrades;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<StudentGrades, Long> {
}
