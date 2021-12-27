package com.alan.monitor1.exception;

public class NotUpdateError extends RuntimeException {
    public NotUpdateError() {
        super();
    }

    public NotUpdateError(String s) {
        super(s);
    }

    public NotUpdateError(String message, Throwable cause) {
        super(message, cause);
    }

    public NotUpdateError(Throwable cause) {
        super(cause);
    }

}
