package nl.bd.sdbackendopdracht.repositories;

import nl.bd.sdbackendopdracht.models.datamodels.Absence;
import nl.bd.sdbackendopdracht.models.datamodels.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {

    @Query("SELECT s FROM Absence s WHERE s.absentStudent.userId = ?1")
    Optional<Set<Absence>> getALlAbsenceFromUser(Long studentId);

    @Query("SELECT s FROM Absence s WHERE s.absentStudent.coursesBelongingToStudent = ?1")
    Optional<Set<Absence>> getALlAbsenceFromCourse(Course course);

    @Query("SELECT s FROM Absence s WHERE s.absentStudent.userId = ?1")
    Optional<Set<Absence>> getAllAbsenceFromSchool(Long schoolId);
}
