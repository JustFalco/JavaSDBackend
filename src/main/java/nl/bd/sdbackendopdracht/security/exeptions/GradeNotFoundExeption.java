package nl.bd.sdbackendopdracht.security.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GradeNotFoundExeption extends RuntimeException {
    /**
     *
     */
    public GradeNotFoundExeption() {
        super();
    }

    /**
     * @param message
     */
    public GradeNotFoundExeption(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public GradeNotFoundExeption(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public GradeNotFoundExeption(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    protected GradeNotFoundExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
