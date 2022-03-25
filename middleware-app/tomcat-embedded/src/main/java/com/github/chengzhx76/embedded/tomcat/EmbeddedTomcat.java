package com.github.chengzhx76.embedded.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import javax.servlet.http.HttpServlet;
import java.io.File;

public class EmbeddedTomcat {

    public static void main( String[] args ) throws LifecycleException {
        int port = 8080;
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("temp");
        tomcat.setPort(port);

        String contextPath = "/";
        String docBase = new File(".").getAbsolutePath();

        Context context = tomcat.addContext(contextPath, docBase);

        HttpServlet servlet = new HelloWorldServlet();
        String servletName = "HelloWorld";
        String urlPattern = "/helloWorld";

        tomcat.addServlet(contextPath, servletName, servlet);
        context.addServletMappingDecoded(urlPattern, servletName);

        FilterDef filterDef = new FilterDef();
        filterDef.setFilterClass(SimpleFilter.class.getName());
        filterDef.setFilterName(SimpleFilter.class.getSimpleName());
        filterDef.addInitParameter("encoding", "UTF-8");
        context.addFilterDef(filterDef);

        FilterMap filterMap = new FilterMap();
        filterMap.setFilterName(SimpleFilter.class.getSimpleName());
        filterMap.addURLPattern("/*");
        context.addFilterMap(filterMap);

        tomcat.start();
        tomcat.getServer().await();

    }

}
