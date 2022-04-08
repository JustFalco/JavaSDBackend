package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByUserId(Long userId){
        return getUserByUserId(userId);
    }

}
