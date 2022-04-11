package nl.bd.sdbackendopdracht.controllers;

import nl.bd.sdbackendopdracht.models.SchoolRegistrationRequest;
import org.springframework.security.core.Authentication;
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

    @RequestMapping(path = "/login")
    public String login(Authentication authentication){
        if(authentication != null){
            return getIndex(authentication);
        }
        return "login";
    }

    @RequestMapping(path = "/dashboard")
    public String getDashboard(){
        return "dashboard";
    }

    @RequestMapping(path = "/")
    public String getIndex(Authentication authentication){
        if(authentication != null){
            return getDashboard();
        }
        return "index";
    }

}
