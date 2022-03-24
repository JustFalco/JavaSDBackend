package nl.bd.sdbackendopdracht.repos;

import nl.bd.sdbackendopdracht.models.Administrator;
import nl.bd.sdbackendopdracht.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepo extends JpaRepository<Administrator, Long> {
}
