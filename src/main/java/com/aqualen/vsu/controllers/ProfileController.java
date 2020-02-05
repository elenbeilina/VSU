package com.aqualen.vsu.controllers;

import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.services.DepartmentService;
import com.aqualen.vsu.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/edit/{id}")
    public String editProfile(@PathVariable long id, ModelMap modelMap){
        modelMap.addAttribute("user", userService.getById(id));
        modelMap.addAttribute("departments", departmentService.getAll());
        return "edit-profile";
    }

    @GetMapping("/{id}")
    public String getProfile(@PathVariable("id") long userId,
                             ModelMap modelMap,
                             Principal principal){
        User user = userService.getById(userId);
        modelMap.addAttribute("isUserProfile", user.getUsername().equals(principal.getName()));
        modelMap.addAttribute("user", user);
        return "profile";
    }

    @PostMapping(value = "/update/{id}", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateProfile(@PathVariable long id, @RequestBody MultiValueMap<String, String> map, ModelMap modelMap){
        User user = userService.getById(id);
        userService.updateUser(user,map);
        modelMap.addAttribute("alertMessage", "Пользователь " + user.getUsername() + " успешно изменен !");
        modelMap.addAttribute("user", user);
        return "profile";
    }
}
