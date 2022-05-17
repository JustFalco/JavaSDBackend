package nl.bd.sdbackendopdracht.security.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class CourseProcessExeption extends RuntimeException {

    /**
     */
    public CourseProcessExeption(String message) {
        super(message);
    }

}
