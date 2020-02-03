package com.aqualen.vsu.controllers;

import com.aqualen.vsu.services.DepartmentService;
import com.aqualen.vsu.services.UserService;
import com.aqualen.vsu.utils.Updater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private DepartmentService departmentService;
    private Updater updater;

    @Autowired
    AdminController(UserService userService,
                    DepartmentService departmentService,
                    Updater updater){
        this.userService = userService;
        this.departmentService = departmentService;
        this.updater = updater;
    }

    @GetMapping("")
    public String welcome(ModelMap modelMap, Principal principal){
        modelMap.addAttribute("adminName", principal.getName());
        return "admin/admin-index";
    }

}
