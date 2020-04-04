package com.aqualen.vsu.core.controllers;

import com.aqualen.vsu.core.aspects.SimpleLog;
import com.aqualen.vsu.core.entity.enums.UserRole;
import com.aqualen.vsu.core.services.UserService;
import com.aqualen.vsu.core.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/sponsors")
public class SponsorsController {

    @Autowired
    UserService userService;

    private static final String DEFAULT_ERROR_MESSAGE = "При работе пользователями произошла непредвиденная ошибка";

    @ExceptionHandler(Exception.class)
    public Response handleException(HttpServletRequest request, Exception e) {
        log.error("Error on processing of URL mapping '" + request.getRequestURI() + "': ", e);
        return Response.fail(DEFAULT_ERROR_MESSAGE);
    }

    @SimpleLog
    @GetMapping("all")
    public Response getAll() {
        return Response.success(userService.getUsersByRole(UserRole.Sponsor));
    }
}
