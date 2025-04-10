package com.smilie.smiliebackend.infrastructure.errorvalidation;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;

@Log4j2
@ControllerAdvice
public class ExceptionsHandlers {

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public DuplicateKeyExceptionDto handleDataIntegrityViolationException() {
        final String loginAlreadyExists = "Login already exists";
        log.warn(loginAlreadyExists);
        return DuplicateKeyExceptionDto.builder()
                .message(loginAlreadyExists)
                .build();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public UsernameNotFoundExceptionDto handleUsernameNotFoundException() {
        final String loginNotFound = "Login not found";
        log.warn(loginNotFound);
        return UsernameNotFoundExceptionDto.builder()
                .message(loginNotFound)
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiValidationErrorResponseDto handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        final List<String> errors = exception.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        log.warn("Validation error{}", errors);
        return ApiValidationErrorResponseDto.builder()
                .errors(errors)
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(JokeNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public UsernameNotFoundExceptionDto handleJokeNotFoundException() {
        final String notFound = "Joke not found";
        log.warn(notFound);
        return UsernameNotFoundExceptionDto.builder()
                .message(notFound)
                .build();
    }

    @ExceptionHandler(AllJokeLikedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public UsernameNotFoundExceptionDto allJokeLikedException() {
        final String notFound = "All joke liked";
        log.warn(notFound);
        return UsernameNotFoundExceptionDto.builder()
                .message(notFound)
                .build();
    }
}
