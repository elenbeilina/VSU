package com.vsu.project.controllers;

import com.vsu.project.services.DepartmentService;
import com.vsu.project.services.UserService;
import com.vsu.project.utils.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
