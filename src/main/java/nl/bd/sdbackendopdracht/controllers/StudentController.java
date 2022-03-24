package nl.bd.sdbackendopdracht.controllers;

import nl.bd.sdbackendopdracht.models.Student;
import nl.bd.sdbackendopdracht.models.User;
import nl.bd.sdbackendopdracht.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(value = "/getAllStudents")
    public List<User> getStudents(){
        return studentService.getStudents();
    }

    @PostMapping(value = "/addNewStudent")
    public void registerNewStudent(@RequestBody Student student){
        studentService.addNewStudent(student);
    }
}
