package com.aqualen.vsu.controllers;

import com.aqualen.vsu.aspects.SimpleLog;
import com.aqualen.vsu.exceptions.PasswordException;
import com.aqualen.vsu.logic.PasswordLogic;
import com.aqualen.vsu.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/password")
public class PasswordController {
    @Autowired
    PasswordLogic passwordLogic;

    private static final String DEFAULT_ERROR_MESSAGE = "При работе с паролями произошла непредвиденная ошибка";

    @ExceptionHandler(Exception.class)
    public Response handleException(HttpServletRequest request, Exception e) {
        log.error("Error on processing of URL mapping '" + request.getRequestURI() + "': ", e);
        return Response.fail(DEFAULT_ERROR_MESSAGE);
    }

    @ExceptionHandler(PasswordException.class)
    public Response handlePasswordException(HttpServletRequest request, Exception e) {
        log.error("Error on processing of URL mapping '" + request.getRequestURI() + "': ", e);
        return Response.fail(e.getMessage());
    }

    @SimpleLog
    @PutMapping("")
    public Response update(@RequestParam String userName, @RequestParam String old,
                           @RequestParam String newOne, @RequestParam String newTwo) {
        passwordLogic.updatePassword(userName, old, newOne, newTwo);
        return Response.success();
    }
}
