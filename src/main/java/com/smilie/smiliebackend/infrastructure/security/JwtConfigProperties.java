package com.smilie.smiliebackend.infrastructure.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "auth.jwt")
public record JwtConfigProperties(
        String secret,
        Long hours
) {
}
