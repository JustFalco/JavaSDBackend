/*
 * Copyright (c) 2022. Falco Wolkorte
 */

package nl.bd.sdbackendopdracht.security.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AbsenceNotFoundExeption extends RuntimeException {

    public AbsenceNotFoundExeption(String message) {
        super(message);
    }

}
