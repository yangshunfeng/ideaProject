package com.smart.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.management.Agent;

/**
 * Created by 972536780 on 2017/6/25.
 */
@RestController
@EnableAutoConfiguration
public class Daemon {
    @RequestMapping("/")
    public String index() {
        return "hello world";
    }

    public static void main(String[] args) {
        SpringApplication.run(Daemon.class, args);
    }
}
