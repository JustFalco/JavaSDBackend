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

    /**
     * De getUserByUserId zoekt in de database naar een gebruiker op basis van het meegegeven id.
     * Als de gebruiker bestaat wordt deze teruggegeven, anders wordt er een error teruggegeven.
     * @param userId -> id van de gebruiker waar gegevens van op worden gevraagd
     * @return User object of een error als de gebruiker niet gevonden kan worden
     */
    public User getUserByUserId(Long userId) {
        User user;
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundExeption("The user with id: " + userId + " has not been found in the database!");
        } else {
            user = userRepository.findById(userId).get();
        }
        return user;
    }

    /**
     * getPersonalUserDetails is een method die een gebruiker opvraagt uit de database op basis
     * van de op dat moment ingelogde gebruiker
     * @param email -> email van de ingelogde gebruiker
     * @return User object of error als de gebruiker niet gevonden kan worden
     */
    public User getPersonalUserDetails(String email) {
        if(!email.equals("Admin")){
            emailValidation.validate(email);
        }
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundExeption("User with email " + email + " does not exists!"));
    }

    /**
     * changeUserDetails is een method die op basis van een email en een DTO een gebruiker ophaalt uit de database, vervolgens
     * de data van deze gebruiker veranderd waar nodig, en daarna weer terug opslaat in de database
     * @param email -> email van de gebruiker die veranderd moet worden
     * @param request -> DTO met informatie over de gebruiker die veranderd moet worden
     * @return User object dat is aangemaakt of een error als het process niet voltooit kon worden
     */
    public User changeUserDetails(String email, UserRegistrationRequest request) {
        emailValidation.validate(email);
        User user = getPersonalUserDetails(email);

        if (request.firstName() != "") {
            user.setFirstName(request.firstName());
        }
        if (request.middleName() != "") {
            user.setMiddleName(request.middleName());
        }
        if (request.lastName() != "") {
            user.setLastName(request.lastName());
        }
        if (request.dateOfBirth() != null) {
            user.setDateOfBirth(request.dateOfBirth());
        }
        if (request.password() != "") {
            user.setPassword(bCryptPasswordEncoder.encode(request.password()));
        }
        if (validation.validateNumber(request.studentYear(), 1, 100)) {
            user.setYear(request.studentYear());
        }

        user.setEnabled(request.isActive());

        return userRepository.save(user);
    }

    /**
     * De method removeUser kijkt of een gebruiker in de database bestaat en dat deze gebruiker geen admin is.
     * Als daaraan is voldaan wordt deze gebruiker uit de database verwijderd.
     * @param userId -> id van de gebruiker die verwijderd moet worden
     */
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
