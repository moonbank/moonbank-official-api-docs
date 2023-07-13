package com.mine.api.demo;

import com.mine.api.demo.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableCustomSwagger2
@SpringBootApplication
public class ApiDocsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiDocsApplication.class, args);
    }
}