package com.lancep.resource;

import com.google.inject.Singleton;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@SuppressWarnings("unused")
@Singleton
@Path("health")
public class HealthResource {

    @GET
    public Response amIAwake() {
        return Response.ok("CSV parser is up and ready.", MediaType.TEXT_PLAIN).build();
    }

}
