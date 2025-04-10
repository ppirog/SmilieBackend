package com.smilie.smiliebackend.infrastructure.loginandregister.controller;

import com.smilie.smiliebackend.infrastructure.loginandregister.controller.dto.RegisterRequestDto;
import com.smilie.smiliebackend.infrastructure.loginandregister.controller.dto.RegisterResponseDto;
import com.smilie.smiliebackend.loginandregister.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.smilie.smiliebackend.loginandregister.dto.UserRequestDto;

@Service
@AllArgsConstructor
class LoginAndRegisterMapper {
    private final PasswordEncoder passwordEncoder;

    UserRequestDto fromReqisterRequestDto(RegisterRequestDto dto) {
        return UserRequestDto.builder()
                .login(dto.login())
                .password(passwordEncoder.encode(dto.password()))
                .build();
    }

    RegisterResponseDto fromUserResponseDto(UserResponseDto dto, String message) {
        return RegisterResponseDto.builder()
                .login(dto.login())
                .message(message)
                .build();
    }

}
