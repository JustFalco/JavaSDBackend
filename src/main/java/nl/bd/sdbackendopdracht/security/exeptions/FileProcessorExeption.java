package nl.bd.sdbackendopdracht.security.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class FileProcessorExeption extends RuntimeException{
    /**
     *
     */
    public FileProcessorExeption() {
        super();
    }

    /**
     * @param message
     */
    public FileProcessorExeption(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public FileProcessorExeption(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public FileProcessorExeption(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    protected FileProcessorExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
