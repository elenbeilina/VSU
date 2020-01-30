package com.aqualen.vsu.controllers;

import com.aqualen.vsu.services.NewsService;
import com.aqualen.vsu.services.UserService;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.utils.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminNewsController {

    private UserService userService;
    private NewsService newsService;
    private Updater updater;

    @Autowired
    AdminNewsController(UserService userService,
                    NewsService newsService,
                    Updater updater){
        this.userService = userService;
        this.newsService = newsService;
        this.updater = updater;
    }

    @GetMapping("/news")
    public String getAllUsers(ModelMap modelMap){
        modelMap.addAttribute("news", newsService.getAll());
        return "admin/admin-news";
    }

    @GetMapping("/news/edit/{id}")
    public String editUser(@PathVariable long id, ModelMap modelMap){
        modelMap.addAttribute("newsPaper", newsService.getById(id));
        return "admin/admin-edit-news";
    }

    @GetMapping("/news/add")
    public String addUser(ModelMap modelMap){
        return "admin/admin-add-news";
    }
}
