package com.lancep;

import com.lancep.api.StreamingCSVMarshallerResource;
import org.glassfish.jersey.server.ResourceConfig;

public class RestApplication extends ResourceConfig {

    public RestApplication() {
        // register application resources
        register(StreamingCSVMarshallerResource.class);
    }
}
