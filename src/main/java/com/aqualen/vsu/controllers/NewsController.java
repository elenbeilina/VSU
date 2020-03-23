package com.aqualen.vsu.controllers;

import com.aqualen.vsu.entity.News;
import com.aqualen.vsu.services.NewsService;
import com.aqualen.vsu.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    private static final String DEFAULT_ERROR_MESSAGE = "При обработке данных произошла непредвиденная ошибка";

    @ExceptionHandler(Exception.class)
    public Response handleException(HttpServletRequest request, Exception e) {
        log.error("Error on processing of URL mapping '" + request.getRequestURI() + "': ", e);
        return Response.fail(DEFAULT_ERROR_MESSAGE);
    }

    @GetMapping("all")
    public Response getNews() {
        return Response.success(newsService.getAll());
    }

    @GetMapping("")
    public Response getOne(@RequestParam Long id) {
        return Response.success(newsService.getById(id));
    }

    @PutMapping("")
    public Response edit(@RequestBody News news) {
        newsService.update(news);
        return Response.success();
    }

    @DeleteMapping("")
    public Response delete(@RequestParam Long id) {
        newsService.delete(id);
        return Response.success();
    }

    @PostMapping("")
    public Response add(@RequestBody News news) {
        newsService.update(news);
        return Response.success();
    }
}
