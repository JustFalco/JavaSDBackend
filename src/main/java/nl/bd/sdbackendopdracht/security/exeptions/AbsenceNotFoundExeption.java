/*
 * Copyright (c) 2022. Falco Wolkorte
 */

package nl.bd.sdbackendopdracht.security.exeptions;

public class AbsenceNotFoundExeption extends RuntimeException{
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
