package nl.bd.sdbackendopdracht.repos;

import nl.bd.sdbackendopdracht.models.Student;
import nl.bd.sdbackendopdracht.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeveloperRepo extends JpaRepository<User, Long> {
}
