package com.smilie.smiliebackend;

import org.springframework.boot.SpringApplication;

public class TestSmilieBackendApplication {

    public static void main(String[] args) {
        SpringApplication.from(SmilieBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
