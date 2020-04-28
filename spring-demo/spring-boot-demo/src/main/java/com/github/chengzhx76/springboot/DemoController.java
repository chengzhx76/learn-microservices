package com.github.chengzhx76.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author admin
 * @Date 2020/4/28 18:33
 * @Version 3.0
 */
@RestController
public class DemoController {

    @GetMapping
    public String test() {
        return "hello world! " + System.currentTimeMillis();
    }

    @GetMapping("api/test")
    public String apiTest() {
        return "apiTest! " + System.currentTimeMillis();
    }

    @GetMapping("test")
    public String api() {
        return "test " + System.currentTimeMillis();
    }

}
