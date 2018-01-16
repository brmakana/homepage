package io.makana.homepage.domain.chronicles;

public class ChroniclesException extends Exception {

    public ChroniclesException() {
    }

    public ChroniclesException(String message) {
        super(message);
    }

    public ChroniclesException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChroniclesException(Throwable cause) {
        super(cause);
    }

    public ChroniclesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
