package com.bsdl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

//    public static class ServletInitializer extends SpringBootServletInitializer {
//        @Override
//        protected SpringApplicationBuilder configure(SpringApplicationBuilder app) {
//            return app.sources(BlogApplication.class);
//        }
//    }
}
