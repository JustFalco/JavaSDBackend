package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.repositories.UserRepository;
import nl.bd.sdbackendopdracht.security.enums.RoleEnums;
import nl.bd.sdbackendopdracht.security.exeptions.EmailAlreadyExistsExeption;
import nl.bd.sdbackendopdracht.models.datamodels.Student;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.models.StudentRegistrationRequest;
import nl.bd.sdbackendopdracht.repositories.DeveloperRepository;
import nl.bd.sdbackendopdracht.repositories.StudentRepository;
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

    private final StudentRepository studentRepository;
    private final DeveloperRepository developerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public List<User> getStudents(){
        return developerRepository.findAll();
    }

    public String registerStudent(StudentRegistrationRequest request){
        boolean isValidEmail = studentRepository.findStudentByEmail(request.getEmail()).isPresent();

        if(isValidEmail){
            throw new EmailAlreadyExistsExeption("Email: " + request.getEmail() + "  already exists");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());
        Student student = new Student(
                request.getFirstName(),
                request.getMiddleName(),
                request.getLastName(),
                RoleEnums.STUDENT,
                request.getEmail(),
                LocalDate.now(),  //TODO fix this shit
                request.getDateOfBirth(),
                encodedPassword,
                request.getStudentNumber(),
                request.getStudentYear(),
                true
        );

        studentRepository.save(student);

        return "Student saved!";

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User does not exists"));
    }
}
