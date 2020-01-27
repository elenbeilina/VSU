package com.vsu.project.controllers;

import com.vsu.project.entity.User;
import com.vsu.project.services.NewsService;
import com.vsu.project.services.UserService;
import com.vsu.project.utils.Updater;
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

    @PostMapping(value = "/users/add", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateUser(@RequestBody MultiValueMap<String, String> map, ModelMap modelMap){
        User user =  new User();
        user = updater.updateUser(user, map);
        userService.addUser(user);
        modelMap.addAttribute("alertMessage", "Пользователь " + user.getUsername() + " успешно создан !");
        modelMap.addAttribute("users", userService.getAll());
        return "admin/admin-users";
    }

    @PostMapping(value = "/users/update/{id}", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateUser(@PathVariable long id, @RequestBody MultiValueMap<String, String> map, ModelMap modelMap){
        User user = userService.getById(id);
        user = updater.updateUser(user, map);
        userService.updateUser(user);
        modelMap.addAttribute("alertMessage", "Пользователь " + user.getUsername() + " успешно изменен !");
        modelMap.addAttribute("users", userService.getAll());
        return "admin/admin-users";
    }

}
