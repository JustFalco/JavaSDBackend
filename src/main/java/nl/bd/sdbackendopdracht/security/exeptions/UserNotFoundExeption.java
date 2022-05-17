package nl.bd.sdbackendopdracht.security.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundExeption extends RuntimeException {
    public UserNotFoundExeption() {
        super();
    }

    public UserNotFoundExeption(String message) {
        super(message);
    }

    public UserNotFoundExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundExeption(Throwable cause) {
        super(cause);
    }

    protected UserNotFoundExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
