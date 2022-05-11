package nl.bd.sdbackendopdracht.repositories;

import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.security.enums.RoleEnums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT s FROM User s WHERE s.email = ?1")
    Optional<User> findUserByEmail(String email);

    @Query("SELECT s FROM User s WHERE s.roleEnums = ?1")
    Optional<User> findByAdminRoleEnum(RoleEnums roleEnums);

    @Query("SELECT s FROM User s WHERE s.userId = ?1")
    Optional<Set<User>> findAllById(Long userId);

    @Query("SELECT s FROM User s WHERE s.school.schoolId = ?1 AND s.roleEnums = ?2")
    Optional<Set<User>> getStudentsOnSchool(Long schoolId, RoleEnums role);

}
