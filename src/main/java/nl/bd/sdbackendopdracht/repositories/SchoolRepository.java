package nl.bd.sdbackendopdracht.repositories;

import nl.bd.sdbackendopdracht.models.datamodels.School;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
    @Query("SELECT s FROM School s WHERE s.schoolMail = ?1")
    Optional<School> finBySchoolMail(String email);

    @Query("SELECT s FROM School s WHERE s.schoolName = ?1")
    Optional<School> findBySchoolName(String name);
}
