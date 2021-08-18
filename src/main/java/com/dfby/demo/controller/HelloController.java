package com.dfby.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String hello(Map<String, Object> map) {
        //通过 map 向前台页面传递数据
        map.put("name", "编程帮（www.biancheng.net）");
        return "hello";
    }
}

