package nl.bd.sdbackendopdracht.controllers;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.requestmodels.StudentRegistrationRequest;
import nl.bd.sdbackendopdracht.services.RegistrationService;
import nl.bd.sdbackendopdracht.services.StudentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private final StudentService studentService;
    private final RegistrationService registrationService;

    @PostMapping(value = "/student_registration")
    public String registerStudent(@RequestBody StudentRegistrationRequest request){
        return registrationService.registerStudent(request);
    }

}
