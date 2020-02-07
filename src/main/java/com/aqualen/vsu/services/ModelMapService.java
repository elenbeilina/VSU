package com.aqualen.vsu.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.security.Principal;

@Service
public class ModelMapService {
    @Autowired
    UserService userService;

    public void addUser(ModelMap modelMap, Principal principal){
        if (principal != null){
            modelMap.addAttribute("user", userService.findByUsername(principal.getName()));
        }
    }
}
