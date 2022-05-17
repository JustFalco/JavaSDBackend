package nl.bd.sdbackendopdracht.security.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class CourseProcessExeption extends RuntimeException {
    /**
     *
     */
    public CourseProcessExeption() {
        super();
    }

    /**
     * @param message
     */
    public CourseProcessExeption(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public CourseProcessExeption(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public CourseProcessExeption(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    protected CourseProcessExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
