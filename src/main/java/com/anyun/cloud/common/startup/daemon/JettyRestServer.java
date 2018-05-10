package com.anyun.cloud.common.startup.daemon;

import com.anyun.cloud.common.context.SystemEnvironment;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class JettyRestServer {
    private final static String PATH = "/api/v1.0";
    private final static String PORT = "8080";
    private final static String PATH_SPEC = "/*";
    private final static String INIT_PARAMETER_NAME = "jersey.config.server.provider.classnames";
    private final static String PACKAGE_NAME = "com.anyun.cloud.api";
    private final static String CHILE_PACKAGE = "true";

    public static void start(){
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(PATH);
        Server jettyServer = new Server(Integer.parseInt(PORT));
        jettyServer.setHandler(context);
        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, PATH_SPEC);
        jerseyServlet.setInitOrder(0);
        String providerNames = SystemEnvironment.getProviderNames(PACKAGE_NAME, Boolean.parseBoolean(CHILE_PACKAGE));
        jerseyServlet.setInitParameter(INIT_PARAMETER_NAME, providerNames);
        try {
            jettyServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            jettyServer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
