package nl.bd.sdbackendopdracht.repositories;

import nl.bd.sdbackendopdracht.models.datamodels.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
