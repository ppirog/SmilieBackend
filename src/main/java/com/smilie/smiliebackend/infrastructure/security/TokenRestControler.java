package com.smilie.smiliebackend.infrastructure.security;

import com.smilie.smiliebackend.infrastructure.security.dto.JwtResponseDto;
import com.smilie.smiliebackend.infrastructure.security.dto.TokenRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/login")
@AllArgsConstructor
class TokenRestControler {

    private final JwtAuthFacade  jwtAuthFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<JwtResponseDto> fetchToken(@RequestBody @Valid TokenRequestDto dto) {
        final JwtResponseDto body = jwtAuthFacade.authenticateAndGenerateToken(dto);
        log.info("Token generated: {}", body);
        return ResponseEntity.ok(body);
    }
}
