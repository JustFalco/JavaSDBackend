package nl.bd.sdbackendopdracht.security.exeptions;

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
