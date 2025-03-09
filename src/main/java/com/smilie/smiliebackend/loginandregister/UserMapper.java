package com.smilie.smiliebackend.loginandregister;

import com.smilie.smiliebackend.loginandregister.dto.UserRequestDto;
import com.smilie.smiliebackend.loginandregister.dto.UserResponseDto;
import org.springframework.stereotype.Service;


@Service
class UserMapper {

    User mapToUser(UserRequestDto userRequestDto) {
        return User.builder()
                .login(userRequestDto.login())
                .password(userRequestDto.password())
                .build();
    }

    UserResponseDto mapToUserResponseDto(User user) {
        return UserResponseDto.builder()
                .login(user.getLogin())
                .password(user.getPassword())
                .build();
    }
}
