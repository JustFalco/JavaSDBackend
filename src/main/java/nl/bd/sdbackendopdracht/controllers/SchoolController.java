package nl.bd.sdbackendopdracht.controllers;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.SchoolRegistrationRequest;
import nl.bd.sdbackendopdracht.models.datamodels.School;
import nl.bd.sdbackendopdracht.services.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
public class SchoolController {

    private final RegistrationService registrationService;

    @RequestMapping(path = "/api/v1/registration/register_school")
    public String returnRegisterSchoolPage(Model model){
        model.addAttribute("school", new SchoolRegistrationRequest());
        return "register_school";
    }

    @PostMapping(value = "/api/v1/registration/register_school")
    public String registerSchool(@ModelAttribute("school") SchoolRegistrationRequest schoolRegistrationRequest){
        registrationService.registerSchool(schoolRegistrationRequest);
        return "redirect:/api/v1/registration/register_school/email_validation";
    }

    @RequestMapping(path = "/api/v1/registration/register_school/email_validation")
    public String registerSchoolValidation(Model model){
        return "emailValidation";
    }
}
