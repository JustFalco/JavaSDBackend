package nl.bd.sdbackendopdracht.controllers;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.security.annotations.AllUserAuthorisation;
import nl.bd.sdbackendopdracht.security.annotations.DeveloperAuthorisation;
import nl.bd.sdbackendopdracht.security.enums.RoleEnums;
import nl.bd.sdbackendopdracht.services.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    //Get user details (All)
    @GetMapping("/user/get_details")
    public User getPersonalDetails(
            Authentication authentication
    ){
        return userService.getPersonalUserDetails(authentication.getName());
    }

    @GetMapping("/user/get_details/user={userId}")
    @DeveloperAuthorisation
    public User getUser(
            @PathVariable("userId") Long id
    ){
        return userService.getUserByUserId(id);
    }

}
