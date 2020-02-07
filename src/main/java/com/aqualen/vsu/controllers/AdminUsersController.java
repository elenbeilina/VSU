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
@RequestMapping("/admin/users")
public class AdminUsersController {
    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private Updater updater;

    @GetMapping("")
    public String getAll(ModelMap modelMap){
        modelMap.addAttribute("users", userService.getAll());
        return "admin/admin-users";
    }

    @GetMapping("/add")
    public String add(ModelMap modelMap){
        modelMap.addAttribute("departments", departmentService.getAll());
        return "admin/admin-add-user";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable long id, ModelMap modelMap){
        modelMap.addAttribute("departments", departmentService.getAll());
        modelMap.addAttribute("user", userService.getById(id));
        return "admin/admin-edit-user";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable long id, @RequestBody MultiValueMap<String, String> map, ModelMap modelMap){
        User user = userService.getById(id);
        userService.updateUser(user,map);
        modelMap.addAttribute("alertMessage", "Пользователь " + user.getUsername() + " успешно изменен !");
        modelMap.addAttribute("users", userService.getAll());
        return "admin/admin-users";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id, ModelMap modelMap){
        User user = userService.getById(id);
        userService.delete(id);
        modelMap.addAttribute("alertMessage", "Пользователь " + user.getUsername() + " успешно удален !");
        modelMap.addAttribute("users", userService.getAll());
        return "admin/admin-users";
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String add(@RequestBody MultiValueMap<String, String> map, ModelMap modelMap){
        User user = userService.addUser(map);
        modelMap.addAttribute("alertMessage", "Пользователь " + user.getUsername() + " успешно создан !");
        modelMap.addAttribute("users", userService.getAll());
        return "admin/admin-users";
    }
}
