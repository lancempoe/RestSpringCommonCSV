package com.lancep.config;

import com.lancep.errorhandling.AppExceptionMapper;
import com.lancep.resource.CSVMarshallerResource;
import com.lancep.resource.HealthResource;
import org.glassfish.jersey.server.ResourceConfig;

public class RestApplication extends ResourceConfig {

    public RestApplication() {
        // register application resources
        register(CSVMarshallerResource.class);
        register(HealthResource.class);

        // register application provider
        register(AppExceptionMapper.class);
    }
}
