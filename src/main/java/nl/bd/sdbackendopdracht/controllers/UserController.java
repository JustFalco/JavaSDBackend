package nl.bd.sdbackendopdracht.controllers;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.services.UserService;
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

    @GetMapping("/user/get_user/user={userId}")
    public Set<User> getUser(@PathVariable Long userId){
        return userService.getUserByUserId2(userId);
    }

}
