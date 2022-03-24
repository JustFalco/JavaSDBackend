package nl.bd.sdbackendopdracht.services;

import nl.bd.sdbackendopdracht.enums.RoleEnums;
import nl.bd.sdbackendopdracht.exeptions.EmailAlreadyExistsExeption;
import nl.bd.sdbackendopdracht.models.Student;
import nl.bd.sdbackendopdracht.models.User;
import nl.bd.sdbackendopdracht.repos.DeveloperRepo;
import nl.bd.sdbackendopdracht.repos.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepo studentRepo;
    private final DeveloperRepo developerRepo;

    @Autowired
    public StudentService(StudentRepo studentRepo, DeveloperRepo developerRepo) {
        this.studentRepo = studentRepo;
        this.developerRepo = developerRepo;
    }

    public List<User> getStudents(){
        return developerRepo.findAll();
    }

    public void addNewStudent(Student student){
        Optional<Student> studentByEmail = studentRepo.findStudentByEmail(student.getEmail());

        if(studentByEmail.isPresent()){
            throw new EmailAlreadyExistsExeption("Email: " + student.getEmail() + "  already exists");
        }else{
            student.setRoleEnums(RoleEnums.ROLE_STUDENT);
            studentRepo.save(student);

        }

    }
}
