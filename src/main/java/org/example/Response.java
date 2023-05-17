package org.example;

import java.io.Serializable;

public class Response implements Serializable {

    private String message;
    private Exception exception;
    private Status status;

    public Response(String message) {
        this.message = message;
    }

    public Response() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
