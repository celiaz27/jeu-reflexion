package com.example.jeu;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.h2.server.web.WebServlet; // ✅ AJOUT
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {

        AnnotationConfigWebApplicationContext context =
                new AnnotationConfigWebApplicationContext();

        // ✅ IMPORTANT : ajoute aussi JpaConfig
        context.register(WebConfig.class, JpaConfig.class);

        DispatcherServlet servlet =
                new DispatcherServlet(context);

        ServletRegistration.Dynamic registration =
                container.addServlet("dispatcher", servlet);

        registration.setLoadOnStartup(1);
        registration.addMapping("/");

        // 🔥 AJOUT H2 CONSOLE
        ServletRegistration.Dynamic h2Servlet =
                container.addServlet("h2-console", new WebServlet());

        h2Servlet.setLoadOnStartup(2);
        h2Servlet.addMapping("/h2-console/*");
    }
}