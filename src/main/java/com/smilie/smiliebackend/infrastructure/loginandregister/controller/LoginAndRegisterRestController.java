package com.smilie.smiliebackend.infrastructure.loginandregister.controller;

import com.smilie.smiliebackend.infrastructure.loginandregister.controller.dto.RegisterRequestDto;
import com.smilie.smiliebackend.infrastructure.loginandregister.controller.dto.RegisterResponseDto;
import com.smilie.smiliebackend.loginandregister.LoginAndRegisterFacade;
import com.smilie.smiliebackend.loginandregister.dto.UserResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@AllArgsConstructor
public class LoginAndRegisterRestController {

    private final LoginAndRegisterFacade loginAndRegisterFacade;
    private final LoginAndRegisterMapper mapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RegisterResponseDto> registerUser(@RequestBody @Valid RegisterRequestDto registerRequestDto) {
        final UserResponseDto userResponseDto = loginAndRegisterFacade.register(mapper.fromReqisterRequestDto(registerRequestDto));
        final RegisterResponseDto registered = mapper.fromUserResponseDto(userResponseDto, "REGISTERED");
        log.info("User registered: {}", registered);
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }

    @GetMapping("/find/{login}")
    public ResponseEntity<UserResponseDto> findUser(@PathVariable String login) {
        final UserResponseDto byUsername = loginAndRegisterFacade.findByUsername(login);
        log.info("User found: {}", byUsername);
        return ResponseEntity.ok(byUsername);
    }

    @PutMapping("/update/{login}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable String login, @RequestBody @Valid RegisterRequestDto registerRequestDto) {
        final UserResponseDto body = loginAndRegisterFacade.updateByLogin(login, mapper.fromReqisterRequestDto(registerRequestDto));
        log.info("User updated: {}", body);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/delete/{login}")
    public ResponseEntity<UserResponseDto> deleteUser(@PathVariable String login) {
        log.info("Deleting user: {}", login);
        return ResponseEntity.ok(loginAndRegisterFacade.deleteUser(login));
    }
}