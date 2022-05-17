package nl.bd.sdbackendopdracht.repositories;

import nl.bd.sdbackendopdracht.models.datamodels.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

}
