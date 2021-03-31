package com.github.chengzhx76.springmvc;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {

    // http://localhost/mvc/user/get/1?n=z&a=19
    @RequestMapping("/get/{id}")
    public void placeholder(HttpServletResponse response, @PathVariable(value = "id") String id) throws IOException {
        System.out.println("HelloWorldController.HelloWorld");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        Map<String, Object> user = new HashMap<String, Object>() {{
            put("id", id);
            put("name", "cheng");
            put("age", 18);
        }};

        response.getWriter().println(JSON.toJSONString(user));
        response.getWriter().flush();
    }
}
