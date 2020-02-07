package com.aqualen.vsu.controllers.admin;

import com.aqualen.vsu.entity.News;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.entity.enums.UserRole;
import com.aqualen.vsu.services.ModelMapService;
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
    private ModelMapService modelMapService;

    @GetMapping("")
    public String getAll(ModelMap modelMap, Principal principal){
        modelMap.addAttribute("news", newsService.getAll());
        return "admin/admin-news";
    }

    @GetMapping("/{id}")
    public String editForm(@PathVariable long id, ModelMap modelMap){
        modelMap.addAttribute("news", newsService.getById(id));
        return "admin/admin-edit-news";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable long id,@RequestBody MultiValueMap<String, String> map, ModelMap modelMap){
        News news = newsService.getById(id);
        newsService.updateNews(news,map);
        modelMap.addAttribute("news", newsService.getAll());
        return "admin/admin-news";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id, ModelMap modelMap){
        News news = newsService.getById(id);
        newsService.delete(id);
        modelMap.addAttribute("alertMessage", "Новость " + news.getTitle() + " успешно удалена !");
        modelMap.addAttribute("news", newsService.getAll());
        return "admin/admin-users";
    }

    @GetMapping("/add")
    public String add(){
        return "admin/admin-add-news";
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addNews(@RequestBody MultiValueMap<String, String> map, ModelMap modelMap, Principal principal){
        News news = newsService.addNews(map, principal);
        modelMap.addAttribute("alertMessage", "Новость " + news.getTitle() + " успешно создана!");
        modelMap.addAttribute("news", newsService.getAll());
        return "admin/admin-news";
    }
}
