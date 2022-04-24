package nl.bd.sdbackendopdracht.controllers;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.models.requestmodels.SchoolRegistrationRequest;
import nl.bd.sdbackendopdracht.models.requestmodels.StudentRegistrationRequest;
import nl.bd.sdbackendopdracht.services.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    //Registreer student
    @PostMapping(value = "/administrator/registration/register_student")
    public User registerStudent(@RequestBody StudentRegistrationRequest request){
        return registrationService.registerStudent(request);
    }

    //Registreer school
    @PostMapping(value = "registration/register_school")
    public String registerSchool(@ModelAttribute("school") SchoolRegistrationRequest schoolRegistrationRequest){
        registrationService.registerSchool(schoolRegistrationRequest);
        //TODO dit klopt niet helemaal
        return "redirect:register_school/email_validation";
    }

    //Registreer administratief medewerker

    //Registreer docent
}
