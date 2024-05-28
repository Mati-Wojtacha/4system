package com.application.user_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@ServletComponentScan
@SpringBootApplication
@ComponentScan("com.application.file_reader")
@ComponentScan("com.application.user_management")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
