package nl.bd.sdbackendopdracht.repositories;

import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.security.enums.RoleEnums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT s FROM User s WHERE s.email = ?1")
    Optional<User> findUserByEmail(String email);

    @Query("SELECT s FROM User s WHERE s.roleEnums = ?1")
    Optional<User> findByAdminRoleEnum(RoleEnums roleEnums);
}
