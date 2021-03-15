package com.github.chengzhx76.springmvc;

public class MvcApplication {
    /*public static void main(String[] args) throws LifecycleException, ServletException {
        int port = 8080;
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("temp");
        tomcat.setPort(port);

        String contextPath = "/";
        String docBase = new File(".").getAbsolutePath();

        StandardContext context = (StandardContext) tomcat.addWebapp(contextPath, docBase);


        context.addApplicationListener(ContextLoaderListener.class.getName());


        GenericServlet servlet = new DispatcherServlet();
        servlet.getServletContext().setInitParameter("contextConfigLocation", "classpath:/spring-mvc.xml");

        String servletName = "dispatcher";
        String urlPattern = "/";

        tomcat.addServlet(contextPath, servletName, servlet);
        context.addServletMappingDecoded(urlPattern, servletName);

        tomcat.start();
        tomcat.getServer().await();
    }*/
}
