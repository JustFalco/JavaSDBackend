package nl.bd.sdbackendopdracht.repositories;

import nl.bd.sdbackendopdracht.models.datamodels.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {

    @Query("SELECT s FROM Administrator s WHERE s.email = ?1")
    Optional<Administrator> findAdministratorByEmail(String email);
}
