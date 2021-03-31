package com.github.chengzhx76.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@Controller
public class HelloWorldController {

    /*@RequestMapping("index")
    public String index(HttpServletRequest request, HttpSession session) {
        System.out.println("------------------");
        return "index";
    }

    @RequestMapping("helloWorld")
    public void index(HttpServletResponse response) throws IOException {
        System.out.println("HelloWorldController.HelloWorld");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println("HelloWorld");
        response.getWriter().flush();
    }*/
}
