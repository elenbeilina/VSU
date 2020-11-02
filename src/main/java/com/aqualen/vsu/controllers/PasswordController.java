package com.aqualen.vsu.controllers;

import com.aqualen.vsu.aspects.SimpleLog;
import com.aqualen.vsu.logic.PasswordLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("password")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordLogic passwordLogic;

    @SimpleLog
    @PutMapping
    public void update(@RequestParam String userName, @RequestParam String old,
                       @RequestParam String newOne, @RequestParam String newTwo) {
        passwordLogic.updatePassword(userName, old, newOne, newTwo);
    }
}
