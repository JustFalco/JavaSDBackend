package nl.bd.sdbackendopdracht.security.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.IM_USED)
public class EmailAlreadyExistsExeption extends RuntimeException {

    public EmailAlreadyExistsExeption(String message) {
        super(message);
    }

}
