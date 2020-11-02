package com.aqualen.vsu.controllers.user;

import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.log.SimpleLog;
import com.aqualen.vsu.services.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @SimpleLog
    @GetMapping
    @ApiOperation(value = "Get user from context")
    public ResponseEntity<User> get() {
        return ResponseEntity.ok(userService.getUser());
    }

    @SimpleLog
    @GetMapping
    public ResponseEntity<User> getUser(@RequestParam Long id) {
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
}
