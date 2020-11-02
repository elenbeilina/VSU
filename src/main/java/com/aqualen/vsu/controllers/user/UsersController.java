package com.aqualen.vsu.controllers.user;

import com.aqualen.vsu.log.SimpleLog;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.enums.UserRole;
import com.aqualen.vsu.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @SimpleLog
    @GetMapping()
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @SimpleLog
    @GetMapping("role")
    public ResponseEntity<List<User>> getUsers(@RequestParam UserRole role) {
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }
}
