package com.lancep.resource.errorhandling;

import javax.ws.rs.core.Response;

public class CsvException extends RuntimeException {

    private static final Response.Status status = Response.Status.CONFLICT;

    public CsvException(String message) {
        super(message);
    }

    public Response.Status getStatus() {
        return status;
    }
}