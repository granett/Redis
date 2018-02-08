package com.redis.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
@ResponseBody
    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

}

