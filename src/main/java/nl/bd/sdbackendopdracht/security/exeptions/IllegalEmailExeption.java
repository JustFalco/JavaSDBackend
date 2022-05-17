/*
 * Copyright (c) 2022. Falco Wolkorte
 */

package nl.bd.sdbackendopdracht.security.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class IllegalEmailExeption extends RuntimeException {
    public IllegalEmailExeption() {
        super();
    }

    public IllegalEmailExeption(String message) {
        super(message);
    }

    public IllegalEmailExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalEmailExeption(Throwable cause) {
        super(cause);
    }

    protected IllegalEmailExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
