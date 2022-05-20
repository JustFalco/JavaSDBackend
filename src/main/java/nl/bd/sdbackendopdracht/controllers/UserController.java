package nl.bd.sdbackendopdracht.controllers;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.models.requestmodels.UserRegistrationRequest;
import nl.bd.sdbackendopdracht.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    //Get user details (All)
    @GetMapping("/user/get_details")
    public User getPersonalDetails(
            Authentication authentication
    ) {
        return userService.getPersonalUserDetails(authentication.getName());
    }

    @GetMapping("/user/get_details/user={userId}")
    public User getUser(
            @PathVariable("userId") Long id
    ) {
        return userService.getUserByUserId(id);
    }

    @PutMapping("/user/change/email={email}")
    public User changeUser(
            @PathVariable("email") String email,
            @RequestBody UserRegistrationRequest request
    ) {
        return userService.changeUserDetails(email, request);
    }

    @GetMapping("/user/get_details/email={email}")
    public User getByEmail(
            @PathVariable("email") String email
    ){
        return userService.getPersonalUserDetails(email);
    }

    @DeleteMapping("/admin/delete/user={userId}")
    public void delete(
            @PathVariable("userId") Long userId
    ){
        userService.removeUser(userId);
    }

}
