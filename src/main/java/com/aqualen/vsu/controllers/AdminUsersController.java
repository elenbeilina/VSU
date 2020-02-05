package com.aqualen.vsu.controllers;

import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.services.DepartmentService;
import com.aqualen.vsu.services.UserService;
import com.aqualen.vsu.utils.Updater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminUsersController {
    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private Updater updater;

    @GetMapping("/users")
    public String getAllUsers(ModelMap modelMap){
        modelMap.addAttribute("users", userService.getAll());
        return "admin/admin-users";
    }

    @GetMapping("/users/add")
    public String addUser(ModelMap modelMap){
        modelMap.addAttribute("departments", departmentService.getAll());
        return "admin/admin-add-user";
    }

    @PostMapping(value = "/users/add", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateUser(@RequestBody MultiValueMap<String, String> map, ModelMap modelMap){
        User user =  new User();
        user = updater.updateUser(user, map);
        userService.addUser(user);
        modelMap.addAttribute("alertMessage", "Пользователь " + user.getUsername() + " успешно создан !");
        modelMap.addAttribute("users", userService.getAll());
        return "admin/admin-users";
    }
}
