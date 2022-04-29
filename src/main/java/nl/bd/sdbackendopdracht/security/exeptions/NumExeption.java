/*
 * Copyright (c) 2022. Falco Wolkorte
 */

package nl.bd.sdbackendopdracht.security.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class NumExeption extends RuntimeException{
    public NumExeption() {
        super();
    }

    public NumExeption(String message) {
        super(message);
    }

    public NumExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public NumExeption(Throwable cause) {
        super(cause);
    }

    protected NumExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
