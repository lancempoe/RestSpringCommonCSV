package com.lancep;

import com.lancep.resource.HealthResource;
import com.lancep.resource.StreamingCSVMarshallerResource;
import com.lancep.resource.errorhandling.AppExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;

public class RestApplication extends ResourceConfig {

    public RestApplication() {
        // register application resources
        register(HealthResource.class);
        register(StreamingCSVMarshallerResource.class);

        // register exception mapper
        register(AppExceptionMapper.class);
    }
}
