package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.models.requestmodels.UserRegistrationRequest;
import nl.bd.sdbackendopdracht.repositories.UserRepository;
import nl.bd.sdbackendopdracht.security.exeptions.UserNotFoundExeption;
import nl.bd.sdbackendopdracht.security.validation.NumValidation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final NumValidation validation = new NumValidation();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User does not exists"));
    }

    /* Global user methods */
    public User getUserByUserId(Long userId){
        User user = null;
        if(userRepository.findById(userId).isEmpty()){
            throw new UserNotFoundExeption("The user with id: " + userId + " has not been found in the database!");
        }else{
           user = userRepository.findById(userId).get();
        }
        return user;
    }

    public User getPersonalUserDetails(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundExeption("User with email " + email + " does not exists!"));
    }

    public User changeUserDetails(Long userId, UserRegistrationRequest request){
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundExeption("User with id: " + userId + " has not been found!"));

        if(request.getFirstName() != null){
            user.setFirstName(request.getFirstName());
        }
        if (request.getMiddleName() != null) {
            user.setMiddleName(request.getMiddleName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if (request.getEmail() != null){
            user.setEmail(request.getEmail());
        }
        if (request.getDateOfBirth() != null) {
            user.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        }
        if (validation.validateNumber(request.getTeacherNumber(), 1, Integer.MAX_VALUE)) {
            user.setTeacherNumber(request.getTeacherNumber());
        }
        if (validation.validateNumber(request.getStudentNumber(), 1, Integer.MAX_VALUE)) {
            user.setStudentNumber(request.getStudentNumber());
        }
        if (validation.validateNumber(request.getStudentYear(), 1, 100)) {
            user.setYear(request.getStudentYear());
        }
        if (validation.validateNumber(request.getWorkerNumber(), 1, Integer.MAX_VALUE)) {
            user.setWorkerNumber(request.getWorkerNumber());
        }

        user.setIsActiveTeacher(request.isActiveTeacher());
        user.setIsActiveWorker(request.isActiveWorker());
        user.setIsActiveStudent(request.isActiveStudent());

        return userRepository.save(user);
    }

}
