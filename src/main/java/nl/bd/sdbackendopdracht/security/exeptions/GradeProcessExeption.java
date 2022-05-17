package nl.bd.sdbackendopdracht.security.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class GradeProcessExeption extends RuntimeException {
    /**
     *
     */
    public GradeProcessExeption() {
        super();
    }

    /**
     * @param message
     */
    public GradeProcessExeption(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public GradeProcessExeption(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public GradeProcessExeption(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    protected GradeProcessExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
