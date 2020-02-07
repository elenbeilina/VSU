package com.aqualen.vsu.controllers.user;

import com.aqualen.vsu.services.ModelMapService;
import com.aqualen.vsu.services.TournamentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/tournaments")
public class TournamentsController {

    @Autowired
    TournamentsService tournamentsService;

    @Autowired
    ModelMapService modelMapService;

    @GetMapping("")
    public String getAll(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("tournaments", tournamentsService.getAll());
        modelMapService.addUser(modelMap, principal);
        return "tournaments";
    }
}
