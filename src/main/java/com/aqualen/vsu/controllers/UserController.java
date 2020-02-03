package com.aqualen.vsu.controllers;

import com.aqualen.vsu.entity.News;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.entity.enums.UserRole;
import com.aqualen.vsu.services.impl.NewsServiceImpl;
import com.aqualen.vsu.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private NewsServiceImpl newsService;

    @GetMapping("/")
    public String index(@RequestParam(value = "errorMessage", required = false) String error,
                        ModelMap modelMap,
                        Principal principal){
        List<User> users = userService.getUsersByRole(UserRole.User);
        List<News> news = newsService.getAll(5);
        modelMap.addAttribute("users", users);
        modelMap.addAttribute("news", news);
        if (error != null){
            modelMap.addAttribute("errorMessage", error);
        }
        if (principal != null){
            modelMap.addAttribute("user", userService.findByUsername(principal.getName()));
        }
        return "index";
    }

    @GetMapping("/login")
    public ModelAndView failLogin(@RequestParam(value = "error", required = false) String error,
                                  ModelMap modelMap){
        modelMap.addAttribute("errorMessage", "Неправильный логин или пароль ! Попробуйте снова !");
        return new ModelAndView("redirect:/", modelMap);
    }

    @GetMapping("/profile/{id}")
    public String failLogin(@PathVariable("id") long userId,
                                  ModelMap modelMap,
                                  Principal principal){
        User user = userService.getById(userId);
        modelMap.addAttribute("isUserProfile", user.getUsername().equals(principal.getName()));
        modelMap.addAttribute("user", user);
        return "profile";
    }

}
