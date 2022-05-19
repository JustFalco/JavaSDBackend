package nl.bd.sdbackendopdracht.security.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Email already exists in the database")
public class EmailAlreadyExistsExeption extends RuntimeException {

    public EmailAlreadyExistsExeption(String message) {
        super(message);
    }

}
