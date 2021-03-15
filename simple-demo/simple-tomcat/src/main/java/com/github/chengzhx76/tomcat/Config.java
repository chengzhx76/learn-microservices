package com.github.chengzhx76.tomcat;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Config implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("Config.contextInitialized");
        ServletContext servletContext = event.getServletContext();
        servletContext.setInitParameter("", "");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // NOOP.
    }

}