package com.aqualen.vsu.controllers;

import com.aqualen.vsu.entity.enums.UserRole;
import com.aqualen.vsu.services.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("")
    public String ratingPage(ModelMap modelMap, Principal principal){
        modelMap.addAttribute("top10", userService.getUsersByRole(UserRole.User, 10));
        modelMap.addAttribute("top100", userService.getUsersByRole(UserRole.User, 100));
        if (principal != null){
            modelMap.addAttribute("user", userService.findByUsername(principal.getName()));
        }
        return "rating";
    }

}
