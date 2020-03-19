package com.aqualen.vsu.controllers.admin;

import com.aqualen.vsu.services.ModelMapService;
import com.aqualen.vsu.services.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/admin/question")
public class AdminQuestionController {
    @Autowired
    ModelMapService modelMapService;
    @Autowired
    QuestionService questionService;

    @GetMapping("")
    public String getPage(ModelMap modelMap, Principal principal) {
        modelMapService.addUser(modelMap, principal);
        modelMap.addAttribute("questions", questionService.getAll());
        return "admin/admin-question";
    }
}
