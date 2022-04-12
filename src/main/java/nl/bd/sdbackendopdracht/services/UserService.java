package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.repositories.UserRepository;
import nl.bd.sdbackendopdracht.security.exeptions.UserNotFoundExeption;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    /* Global user methods */

    public User getUserByUserId(Long userId){
        //TODO validation
        return userRepository.getById(userId);
    }

    //TODO replace
    public User getPersonalUserDetails(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundExeption("User with email " + email + " does not exists!"));
    }


    /* Student specific methods */

    /* Teacher specific methods */

    /* Administrator specific methods */
}
