package com.lancep.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.lancep.errorhandling.AppExceptionMapper;
import com.lancep.resource.CSVMarshallerResource;
import com.lancep.resource.HealthResource;
import com.lancep.service.CSVMarshallerService;
import com.lancep.service.impl.CSVMarshallerServiceImpl;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import javax.servlet.annotation.WebListener;

@WebListener
public class GuiceInitializer extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {

        return Guice.createInjector(new JerseyServletModule() {
            @Override
            protected void configureServlets() {
                bindServices();
                bindRestEndpoints();

                serve("/api/*").with(GuiceContainer.class);
            }

            private void bindServices() {
                bind(CSVMarshallerService.class).to(CSVMarshallerServiceImpl.class);
            }

            private void bindRestEndpoints() {
                bind(AppExceptionMapper.class);
                bind(CSVMarshallerResource.class);
                bind(HealthResource.class);
            }
        });
    }
}