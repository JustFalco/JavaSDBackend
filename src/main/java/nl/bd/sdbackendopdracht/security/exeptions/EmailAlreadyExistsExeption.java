package nl.bd.sdbackendopdracht.security.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.IM_USED)
public class EmailAlreadyExistsExeption extends RuntimeException {
    public EmailAlreadyExistsExeption() {
        super();
    }

    public EmailAlreadyExistsExeption(String message) {
        super(message);
    }

    public EmailAlreadyExistsExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAlreadyExistsExeption(Throwable cause) {
        super(cause);
    }

    protected EmailAlreadyExistsExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
