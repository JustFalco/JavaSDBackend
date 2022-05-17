package nl.bd.sdbackendopdracht.security.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class AbsenceProcessorExeption extends RuntimeException {
    /**
     *
     */
    public AbsenceProcessorExeption() {
        super();
    }

    /**
     * @param message
     */
    public AbsenceProcessorExeption(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public AbsenceProcessorExeption(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public AbsenceProcessorExeption(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    protected AbsenceProcessorExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
