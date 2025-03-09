package com.smilie.smiliebackend;

import com.smilie.smiliebackend.infrastructure.security.JwtConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = JwtConfigProperties.class)
public class SmilieBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmilieBackendApplication.class, args);
    }
}
