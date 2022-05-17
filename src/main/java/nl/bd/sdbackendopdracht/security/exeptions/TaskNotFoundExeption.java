package nl.bd.sdbackendopdracht.security.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskNotFoundExeption extends RuntimeException{
    public TaskNotFoundExeption() {
        super();
    }

    public TaskNotFoundExeption(String message) {
        super(message);
    }

    public TaskNotFoundExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskNotFoundExeption(Throwable cause) {
        super(cause);
    }

    protected TaskNotFoundExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
