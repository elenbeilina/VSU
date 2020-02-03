package com.aqualen.vsu.controllers;

import com.aqualen.vsu.services.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin/news")
public class AdminNewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("")
    public String getAllUsers(ModelMap modelMap){
        modelMap.addAttribute("news", newsService.getAll());
        return "admin/admin-news";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable long id, ModelMap modelMap){
        modelMap.addAttribute("newsPaper", newsService.getById(id));
        return "admin/admin-edit-news";
    }

    @GetMapping("/add")
    public String addUser(ModelMap modelMap){
        return "admin/admin-add-news";
    }
}
