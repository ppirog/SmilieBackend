package com.smilie.smiliebackend.infrastructure.errorvalidation;

import lombok.Builder;

@Builder
public record DuplicateKeyExceptionDto(
        String message
) {
}
