package com.aqualen.vsu.controllers;

import com.aqualen.vsu.aspects.SimpleLog;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.services.UserService;
import com.aqualen.vsu.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    private static final String DEFAULT_ERROR_MESSAGE = "При работе с пользователями произошла непредвиденная ошибка";

    @ExceptionHandler(Exception.class)
    public Response handleException(HttpServletRequest request, Exception e) {
        log.error("Error on processing of URL mapping '" + request.getRequestURI() + "': ", e);
        return Response.fail(DEFAULT_ERROR_MESSAGE);
    }

    @SimpleLog
    @GetMapping("all")
    public Response getUsers() {
        return Response.success(userService.getAll());
    }

    @SimpleLog
    @GetMapping("")
    public Response getOne(@RequestParam Long id) {
        return Response.success(userService.getById(id));
    }

    @SimpleLog
    @PutMapping("")
    public Response edit(@RequestBody User user) {
        userService.update(user);
        return Response.success();
    }

    @SimpleLog
    @DeleteMapping("")
    public Response delete(@RequestParam Long id) {
        userService.delete(id);
        return Response.success();
    }

    @SimpleLog
    @PostMapping("")
    public Response add(@RequestBody User user) {
        userService.add(user);
        return Response.success();
    }
}
