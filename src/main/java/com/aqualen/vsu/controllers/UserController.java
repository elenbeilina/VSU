package com.aqualen.vsu.controllers;

import com.aqualen.vsu.aspects.SimpleLog;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @SimpleLog
    @GetMapping("all")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @SimpleLog
    @GetMapping
    public ResponseEntity<User> getOne(@RequestParam Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @SimpleLog
    @PutMapping
    public void edit(@RequestBody User user) {
        userService.update(user);
    }

    @SimpleLog
    @DeleteMapping
    public void delete(@RequestParam Long id) {
        userService.delete(id);
    }

    @SimpleLog
    @PostMapping
    public ResponseEntity<User> add(@RequestBody User user) {
        return ResponseEntity.ok(userService.add(user));
    }
}
