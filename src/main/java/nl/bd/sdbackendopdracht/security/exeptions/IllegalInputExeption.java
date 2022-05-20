package nl.bd.sdbackendopdracht.security.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalInputExeption extends RuntimeException{
    /**
     * @param message
     */
    public IllegalInputExeption(String message) {
        super(message);
    }
}
