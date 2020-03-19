package com.aqualen.vsu.controllers.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("")
    public String welcomePage(ModelMap modelMap, Principal principal){
        modelMap.addAttribute("adminName", principal.getName());
        return "admin/admin-index";
    }

}
