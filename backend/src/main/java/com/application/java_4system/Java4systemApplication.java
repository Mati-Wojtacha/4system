package com.application.java_4system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.application.file_reader")
@ComponentScan("com.application.java_4system")
public class Java4systemApplication {

    public static void main(String[] args) {
        SpringApplication.run(Java4systemApplication.class, args);
    }
}
