package com.example.springchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/*
@SpringBootApplication
public class SpringchatApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringchatApplication.class, args);
    }

}
*/

@SpringBootApplication
public class SpringchatApplication extends SpringBootServletInitializer {


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringchatApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringchatApplication.class, args);
    }
}
