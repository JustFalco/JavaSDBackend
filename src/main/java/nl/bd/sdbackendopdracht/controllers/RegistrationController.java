package nl.bd.sdbackendopdracht.controllers;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.models.requestmodels.SchoolRegistrationRequest;
import nl.bd.sdbackendopdracht.models.requestmodels.UserRegistrationRequest;
import nl.bd.sdbackendopdracht.services.RegistrationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    //Registreer student
    @PostMapping("/administrator/registration/register_student")
    public User registerStudent(@RequestBody UserRegistrationRequest request) {
        return registrationService.registerStudent(request);
    }

    //Registreer school
    @PostMapping("/registration/register_school")
    public User registerSchool(@RequestBody SchoolRegistrationRequest schoolRegistrationRequest) {
        return registrationService.registerSchool(schoolRegistrationRequest);
    }

    //Registreer administratief medewerker
    @PostMapping("/administrator/registration/register_administrator")
    public User registerAdministrator(@RequestBody UserRegistrationRequest request) {
        return registrationService.registerAdministrator(request);
    }

    //Registreer docent
    @PostMapping("/administrator/registration/register_teacher")
    public User registerTeacher(@RequestBody UserRegistrationRequest request) {
        return registrationService.registerTeacher(request);
    }
}
