package com.lancep.errorhandling;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class AppExceptionMapper implements ExceptionMapper<CsvException> {

    private static final Logger logger = Logger.getLogger( AppExceptionMapper.class.getName() );

    public Response toResponse(CsvException ex) {
        logger.warning(ex.getMessage());
        return Response.status(ex.getStatus())
                .entity(ex.getMessage())
                .type(MediaType.APPLICATION_JSON).
                        build();
    }

}
