package com.aqualen.vsu.controllers;

import com.aqualen.vsu.services.ModelMapService;
import com.aqualen.vsu.services.NewsService;
import com.aqualen.vsu.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    ModelMapService modelMapService;

    @GetMapping("")
    public String getAll(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("news", newsService.getAll());
        modelMapService.addUser(modelMap,principal);
        return "news";
    }
}
