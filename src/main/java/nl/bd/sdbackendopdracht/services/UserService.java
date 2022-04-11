package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.repositories.UserRepository;
import nl.bd.sdbackendopdracht.security.exeptions.UserNotFoundExeption;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /* Global user methods */

    public User getUserByUserId(Long userId){
        return getUserByUserId(userId);
    }

    public User getPersonalUserDetails(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundExeption("User with email " + email + " does not exists!"));
    }

    /* Student specific methods */

    /* Teacher specific methods */

    /* Administrator specific methods */
}
