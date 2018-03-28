/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.jetty;

import java.io.File;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 *
 * @author administrador
 */
public class OneWebAppWithJsp {

    public static void main(String[] args) throws Exception {
        // Create a basic jetty server object that will listen on port 8080.
        // Note that if you set this to port 0 then
        // a randomly available port will be assigned that you can either look
        // in the logs for the port,
        // or programmatically obtain it for use in test cases.
        Server server = new Server(8080);

        // Setup JMX
//        MBeanContainer mbContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
//        server.addBean(mbContainer);
        // The WebAppContext is the entity that controls the environment in
        // which a web application lives and
        // breathes. In this example the context path is being set to "/" so it
        // is suitable for serving root context
        // requests and then we see it setting the location of the war. A whole
        // host of other configurations are
        // available, ranging from configuring to support annotation scanning in
        // the webapp (through
        // PlusConfiguration) to choosing where the webapp will unpack itself.
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
//        File warFile = new File("../../jetty-distribution/target/distribution/demo-base/webapps/test.war");
        File warFile = new File("/home/rgonzalez/trabajo/rgonzalez/dev/projects/jetty-war/dist/jetty-war.war");
        if (!warFile.exists()) {
            throw new RuntimeException("Unable to find WAR File: " + warFile.getAbsolutePath());
        }
        webapp.setWar(warFile.getAbsolutePath());
        webapp.setExtractWAR(true);

        // This webapp will use jsps and jstl. We need to enable the
        // AnnotationConfiguration in order to correctly
        // set up the jsp container
        Configuration.ClassList classlist = Configuration.ClassList.setServerDefault(server);
        classlist.addBefore(
                "org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
                "org.eclipse.jetty.annotations.AnnotationConfiguration"
        );

        // Set the ContainerIncludeJarPattern so that jetty examines these
        // container-path jars for tlds, web-fragments etc.
        // If you omit the jar that contains the jstl .tlds, the jsp engine will
        // scan for them instead.
        webapp.setAttribute(
                "org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
                ".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/[^/]*taglibs.*\\.jar$"
        );

        // A WebAppContext is a ContextHandler as well so it needs to be set to
        // the server so it is aware of where to
        // send the appropriate requests.
        server.setHandler(webapp);

        // Configure a LoginService.
        // Since this example is for our test webapp, we need to setup a
        // LoginService so this shows how to create a very simple hashmap based
        // one. The name of the LoginService needs to correspond to what is
        // configured in the webapp's web.xml and since it has a lifecycle of
        // its own we register it as a bean with the Jetty server object so it
        // can be started and stopped according to the lifecycle of the server
        // itself.
//        HashLoginService loginService = new HashLoginService();
//        loginService.setName("Test Realm");
//        loginService.setConfig("src/test/resources/realm.properties");
//        server.addBean(loginService);

        // Start things up! 
        server.start();

//        server.dumpStdErr();

        // The use of server.join() the will make the current thread join and
        // wait until the server is done executing.
        // See http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#join()
        server.join();
    }
}
