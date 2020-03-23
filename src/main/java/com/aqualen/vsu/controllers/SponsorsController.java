package com.aqualen.vsu.controllers;

import com.aqualen.vsu.entity.enums.UserRole;
import com.aqualen.vsu.services.UserService;
import com.aqualen.vsu.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/sponsors")
public class SponsorsController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public Response get() {
        return Response.success(userService.getUsersByRole(UserRole.Sponsor));
    }
}
