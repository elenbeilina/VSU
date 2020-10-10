package com.aqualen.vsu.config;

import com.aqualen.vsu.exceptions.PasswordException;
import com.aqualen.vsu.exceptions.ReadableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class RestControllerAdviser {

    private static final String DEFAULT_MESSAGE = "Произошла непредвиденная ошибка";

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity defaultExceptionHandler(HttpServletRequest request, Exception e) {
        log.error("Error on processing of URL {}: ", request.getRequestURI(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DEFAULT_MESSAGE);
    }

    @ResponseBody
    @ExceptionHandler(ReadableException.class)
    public ResponseEntity defaultExceptionHandler(HttpServletRequest request, ReadableException e) {
        log.error("Error on processing of URL {}:", request.getRequestURI(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity methodArgumentNotValidException(HttpServletRequest request, ConstraintViolationException e) {
        log.error("Error on processing of URL {}:", request.getRequestURI(), e);
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        List<ConstraintViolation<?>> violations = new ArrayList<>(constraintViolations);
        String errorMessage = violations.get(0).getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(PasswordException.class)
    public ResponseEntity handlePasswordException(HttpServletRequest request, Exception e) {
        log.error("Error on processing of URL mapping '" + request.getRequestURI() + "': ", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
}