package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.SchoolRegistrationRequest;
import nl.bd.sdbackendopdracht.models.datamodels.School;
import nl.bd.sdbackendopdracht.repositories.UserRepository;
import nl.bd.sdbackendopdracht.security.enums.RoleEnums;
import nl.bd.sdbackendopdracht.security.exeptions.EmailAlreadyExistsExeption;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.models.StudentRegistrationRequest;
import nl.bd.sdbackendopdracht.security.exeptions.UserNotFoundExeption;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentService implements UserDetailsService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public List<User> getStudents(){
        return userRepository.findAll();
    }


    public String registerStudent(StudentRegistrationRequest request){
        boolean isValidEmail = userRepository.findUserByEmail(request.getEmail()).isPresent();

        if(isValidEmail){
            throw new EmailAlreadyExistsExeption("Email: " + request.getEmail() + "  already exists");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());
        User student = User.builder()
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .roleEnums(RoleEnums.STUDENT)
                .email(request.getEmail())
                .dateOfBirth(request.getDateOfBirth())
                .dateOfCreation(LocalDate.now())
                .password(encodedPassword)
                .studentNumber(request.getStudentNumber())
                .year(request.getStudentYear())
                .locked(false)
                .enabled(true)
                .build();


        userRepository.save(student);

        return "Student saved!";

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User does not exists"));
    }


    public User getPersonalUserDetails(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundExeption("User with email " + email + " does not exists!"));
    }


}
