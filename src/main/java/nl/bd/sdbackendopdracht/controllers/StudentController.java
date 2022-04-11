package nl.bd.sdbackendopdracht.controllers;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.services.StudentService;
import nl.bd.sdbackendopdracht.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final UserService userService;
    @GetMapping(value = "/get_all_students")
    public List<User> getStudents(){
        return studentService.getStudents();
    }

    @GetMapping(value = "/get_personal_details")
    public User personalDetails(Authentication authentication){
        return userService.getPersonalUserDetails(authentication.getName());
    }
}
