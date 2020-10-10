package com.aqualen.vsu.controllers;

import com.aqualen.vsu.aspects.SimpleLog;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.enums.UserRole;
import com.aqualen.vsu.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sponsors")
@RequiredArgsConstructor
public class SponsorsController {

    private final UserService userService;

    @SimpleLog
    @GetMapping("all")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getUsersByRole(UserRole.Sponsor));
    }
}
