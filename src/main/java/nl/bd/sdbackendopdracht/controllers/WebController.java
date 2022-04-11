package nl.bd.sdbackendopdracht.controllers;

import nl.bd.sdbackendopdracht.models.SchoolRegistrationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping(path = "/api/v1/registration/register_school/email_validation")
    public String registerSchoolValidation(Model model){
        return "emailValidation";
    }

    @RequestMapping(path = "/api/v1/registration/register_school")
    public String returnRegisterSchoolPage(Model model){
        model.addAttribute("school", new SchoolRegistrationRequest());
        return "register_school";
    }

    @RequestMapping("/dashboard")
    public String getDashboard(){
        return "dashboard";
    }
}
