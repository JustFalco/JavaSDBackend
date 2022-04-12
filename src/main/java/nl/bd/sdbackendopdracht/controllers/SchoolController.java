package nl.bd.sdbackendopdracht.controllers;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.requestmodels.SchoolRegistrationRequest;
import nl.bd.sdbackendopdracht.services.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class SchoolController {

    private final RegistrationService registrationService;

    @PostMapping(value = "/api/v1/registration/register_school")
    public String registerSchool(@ModelAttribute("school") SchoolRegistrationRequest schoolRegistrationRequest){
        registrationService.registerSchool(schoolRegistrationRequest);
        return "redirect:/api/v1/registration/register_school/email_validation";
    }


}
