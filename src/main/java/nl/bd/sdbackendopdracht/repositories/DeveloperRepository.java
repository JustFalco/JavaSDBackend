package nl.bd.sdbackendopdracht.repositories;

import nl.bd.sdbackendopdracht.models.datamodels.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DeveloperRepository extends JpaRepository<User, Long> {

    @Query("SELECT s FROM User s WHERE s.roleEnums = 'ROLE_DEVELOPER'")
    Optional<User> findByAdminRoleEnum();
}
