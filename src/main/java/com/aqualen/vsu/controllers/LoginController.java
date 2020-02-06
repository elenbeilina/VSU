package com.aqualen.vsu.controllers;

import com.aqualen.vsu.entity.News;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.entity.enums.UserRole;
import com.aqualen.vsu.services.NewsService;
import com.aqualen.vsu.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private NewsService newsService;

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
            User user = userService.findByUsername(principal.getName());
            if(user.getRole().equals(UserRole.Administrator)){
                modelMap.addAttribute("adminName", principal.getName());
                return "admin/admin-index";
            }
            modelMap.addAttribute("user", user);
        }
        return "index";
    }

    @GetMapping("/login")
    public ModelAndView failLogin(@RequestParam(value = "error", required = false) String error,
                                  ModelMap modelMap){
        modelMap.addAttribute("errorMessage", "Неправильный логин или пароль ! Попробуйте снова !");
        return new ModelAndView("redirect:/", modelMap);
    }
}
