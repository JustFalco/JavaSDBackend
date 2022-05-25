package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.repositories.UserRepository;
import nl.bd.sdbackendopdracht.security.enums.RoleEnums;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class SchoolService {

    private final UserRepository userRepository;

    //Methode voor het verkrijgen van alle studenten binnen een school object
    public Set<User> getAllStudentsOnSchool(Long schoolId) {
        return userRepository.getStudentsOnSchool(schoolId, RoleEnums.STUDENT).orElseThrow(() -> new IllegalStateException("Iets ging fout"));
    }
}
