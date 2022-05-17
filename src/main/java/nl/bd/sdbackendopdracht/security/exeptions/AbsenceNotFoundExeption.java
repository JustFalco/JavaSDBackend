/*
 * Copyright (c) 2022. Falco Wolkorte
 */

package nl.bd.sdbackendopdracht.security.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AbsenceNotFoundExeption extends RuntimeException {
    public AbsenceNotFoundExeption() {
        super();
    }

    public AbsenceNotFoundExeption(String message) {
        super(message);
    }

    public AbsenceNotFoundExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public AbsenceNotFoundExeption(Throwable cause) {
        super(cause);
    }

    protected AbsenceNotFoundExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
