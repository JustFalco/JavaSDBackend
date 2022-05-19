package nl.bd.sdbackendopdracht.security.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User not found in database")
public class UserNotFoundExeption extends RuntimeException {

    public UserNotFoundExeption(String message) {
        super(message);
    }

}
