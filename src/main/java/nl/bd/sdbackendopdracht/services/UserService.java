package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.models.requestmodels.UserRegistrationRequest;
import nl.bd.sdbackendopdracht.repositories.UserRepository;
import nl.bd.sdbackendopdracht.security.enums.RoleEnums;
import nl.bd.sdbackendopdracht.security.exeptions.UserNotFoundExeption;
import nl.bd.sdbackendopdracht.security.exeptions.UserProcessExeption;
import nl.bd.sdbackendopdracht.security.validation.EmailValidation;
import nl.bd.sdbackendopdracht.security.validation.NumValidation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final NumValidation validation = new NumValidation();

    private final EmailValidation emailValidation;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User does not exists"));
    }

    /* Global user methods */
    public User getUserByUserId(Long userId) {
        User user;
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundExeption("The user with id: " + userId + " has not been found in the database!");
        } else {
            user = userRepository.findById(userId).get();
        }
        return user;
    }

    public User getPersonalUserDetails(String email) {
        if(!email.equals("Admin")){
            emailValidation.validate(email);
        }
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundExeption("User with email " + email + " does not exists!"));
    }

    public User changeUserDetails(String email, UserRegistrationRequest request) {
        emailValidation.validate(email);
        User user = getPersonalUserDetails(email);

        if (request.firstName() != null) {
            user.setFirstName(request.firstName());
        }
        if (request.middleName() != null) {
            user.setMiddleName(request.middleName());
        }
        if (request.lastName() != null) {
            user.setLastName(request.lastName());
        }
        if (request.dateOfBirth() != null) {
            user.setDateOfBirth(request.dateOfBirth());
        }
        if (request.password() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(request.password()));
        }
        if (validation.validateNumber(request.studentYear(), 1, 100)) {
            user.setYear(request.studentYear());
        }

        user.setEnabled(request.isActive());

        return userRepository.save(user);
    }

    public void removeUser(Long userId){
        boolean empty = userRepository.findById(userId).isEmpty();
        if(empty){
            throw new UserNotFoundExeption("User could not be deleted from database because user with id: " + userId + " does not exists");
        }
        if(userRepository.findById(userId).get().getRoleEnums() == RoleEnums.DEVELOPER || userId == 1){
            throw new UserProcessExeption("Cannot delete admin!");
        }
        userRepository.deleteById(userId);
    }

}
