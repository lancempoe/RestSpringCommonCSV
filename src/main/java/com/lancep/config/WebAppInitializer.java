package com.lancep.config;

import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration.Dynamic;


public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {

        //Clear out reference to applicationContext.xml
        servletContext.setInitParameter("contextConfigLocation", "");

        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext =
                new AnnotationConfigWebApplicationContext();
        rootContext.register(SpringInjector.class);

        // Manage the lifecycle of the root application context
        servletContext.addListener(new ContextLoaderListener(rootContext));

        //Add jersey servlet
        ServletContainer jerseyServlet = new ServletContainer(new RestApplication());
        Dynamic servlet = servletContext.addServlet("jersey-servlet", jerseyServlet);
        servlet.addMapping("/api/*");
        servlet.setLoadOnStartup(1);
    }

}