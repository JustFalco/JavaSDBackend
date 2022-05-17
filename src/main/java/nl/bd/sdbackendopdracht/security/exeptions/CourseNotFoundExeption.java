/*
 * Copyright (c) 2022. Falco Wolkorte
 */

package nl.bd.sdbackendopdracht.security.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CourseNotFoundExeption extends RuntimeException {
    public CourseNotFoundExeption() {
        super();
    }

    public CourseNotFoundExeption(String message) {
        super(message);
    }

    public CourseNotFoundExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseNotFoundExeption(Throwable cause) {
        super(cause);
    }

    protected CourseNotFoundExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
