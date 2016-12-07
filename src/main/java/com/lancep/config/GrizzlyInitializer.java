package com.lancep.config;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.logging.Logger;

public class GrizzlyInitializer {

    private static final Logger logger = Logger.getLogger(GrizzlyInitializer.class.getName());

    public static void main(String[] args) throws Exception {

        // Create HttpServer
        final URI uri = UriBuilder.fromUri("http://127.0.0.1/").port(8080).build();
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, false);

        // Create Web application context
        final WebappContext context = new WebappContext("A simple api that streams back a csv file", "");

        // Set Spring lifecycle of the root application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(SpringInjector.class);
        context.addListener(new ContextLoaderListener(rootContext));

        // Initialize and register Jersey ServletContainer
        final ServletRegistration servletRegistration = context.addServlet("ServletContainer", ServletContainer.class);
        servletRegistration.addMapping("/api/*");
        servletRegistration.setInitParameter( "javax.ws.rs.Application", "com.lancep.config.RestApplication");

        context.deploy(server);

        // register shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("Stopping server..");
                server.shutdown();
            }
        }, "shutdownHook"));

        try {
            server.start();
            logger.info(
                    String.format("Jersey app started at %s:%d. Press CTRL^C to exit..", uri.getHost(), uri.getPort()));
            Thread.currentThread().join();
        } catch (Exception e) {
            logger.severe(
                    String.format("There was an error while starting Grizzly HTTP server. %s", e.getMessage()));
        }
    }
}
