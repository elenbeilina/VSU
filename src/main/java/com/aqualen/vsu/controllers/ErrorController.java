package com.aqualen.vsu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController{

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, ModelMap modelMap){
        int errorCode = getErrorCode(request);
        ServletWebRequest servletWebRequest = new ServletWebRequest(request);
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(servletWebRequest, true);
        switch (errorCode){
            case 400:
                break;
            case 401:
                break;
            case 403:
                break;
            case 404:
                break;
            case 500:
                break;
        }
        modelMap.addAttribute("errorMessage", "Вот это прикол ???");
        return "redirect:/";
    }

    @RequestMapping("/403")
    public String getErrorAccessForbiden(ModelMap modelMap){
        modelMap.addAttribute("errorMessage", "Доступ к запрашиваемой страницы ограничен !");
        return "redirect:/";
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
