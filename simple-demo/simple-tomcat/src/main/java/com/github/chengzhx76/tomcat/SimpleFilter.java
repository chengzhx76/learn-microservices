package com.github.chengzhx76.tomcat;

import javax.servlet.*;
import java.io.IOException;

public class SimpleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("HelloWorldFilter.init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("HelloWorldFilter.doFilter");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("HelloWorldFilter.destroy");
    }
}
