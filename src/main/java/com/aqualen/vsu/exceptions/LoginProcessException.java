package com.aqualen.vsu.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Не существует пользователя с таким именем")
public class LoginProcessException extends RuntimeException {
    public LoginProcessException(String exception) {
        super(exception);
    }
}
