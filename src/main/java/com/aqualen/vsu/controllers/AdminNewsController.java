package com.aqualen.vsu.controllers;

import com.aqualen.vsu.entity.News;
import com.aqualen.vsu.services.NewsService;
import com.aqualen.vsu.services.UserService;
import com.aqualen.vsu.utils.Updater;
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
@RequestMapping("/admin/news")
public class AdminNewsController {

    @Autowired
    private NewsService newsService;
    @Autowired
    Updater updater;
    @Autowired
    private UserService userService;

    @GetMapping("/edit/{id}")
    public String editNews(@PathVariable long id, ModelMap modelMap){
        modelMap.addAttribute("newsPaper", newsService.getById(id));
        return "admin/admin-add-news";
    }

    @GetMapping("/add")
    public String addNews(ModelMap modelMap){
        return "admin/admin-add-news";
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addNews(@RequestBody MultiValueMap<String, String> map, ModelMap modelMap, Principal principal){
        News news = newsService.addNews(map, principal);
        modelMap.addAttribute("alertMessage", "Новость " + news.getTitle() + " успешно создана!");
        modelMap.addAttribute("news", newsService.getAll());
        return "admin/admin-news";
    }

    @GetMapping("")
    public String getAllNews(ModelMap modelMap, Principal principal){
        modelMap.addAttribute("news", newsService.getAll());
        if (principal != null){
            modelMap.addAttribute("user", userService.findByUsername(principal.getName()));
        }
        return "admin/admin-news";
    }
}
