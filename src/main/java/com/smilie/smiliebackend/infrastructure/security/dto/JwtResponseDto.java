package com.smilie.smiliebackend.infrastructure.security.dto;

import lombok.Builder;

@Builder
public record JwtResponseDto(
        String login,
        String token
) {
}
