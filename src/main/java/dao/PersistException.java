package dao;

public class PersistException extends Exception {

    public PersistException() {
    }

    public PersistException(String message) {
        super(message);
    } //суперкласс

    public PersistException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistException(Throwable cause) {
        super(cause);
    }

    public PersistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
