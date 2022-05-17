package nl.bd.sdbackendopdracht.security.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GradeNotFoundExeption extends RuntimeException {

    /**
     *
     */
    public GradeNotFoundExeption(String message) {
        super(message);
    }

}
