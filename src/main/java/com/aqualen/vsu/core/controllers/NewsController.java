package com.aqualen.vsu.core.controllers;

import com.aqualen.vsu.core.aspects.SimpleLog;
import com.aqualen.vsu.core.entity.News;
import com.aqualen.vsu.core.services.NewsService;
import com.aqualen.vsu.core.utils.Response;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    private static final String DEFAULT_ERROR_MESSAGE = "При работе с новостями произошла непредвиденная ошибка";

    @ExceptionHandler(Exception.class)
    public Response handleException(HttpServletRequest request, Exception e) {
        log.error("Error on processing of URL mapping '" + request.getRequestURI() + "': ", e);
        return Response.fail(DEFAULT_ERROR_MESSAGE);
    }

    @SimpleLog
    @GetMapping("all")
    @ApiOperation(value = "Получить все новости")
    public Response getNews() {
        return Response.success(newsService.getAll());
    }

    @SimpleLog
    @GetMapping("")
    public Response getOne(@RequestParam Long id) {
        return Response.success(newsService.getById(id));
    }

    @SimpleLog
    @PutMapping("")
    public Response edit(@RequestBody News news) {
        newsService.update(news);
        return Response.success();
    }

    @SimpleLog
    @DeleteMapping("")
    public Response delete(@RequestParam Long id) {
        newsService.delete(id);
        return Response.success();
    }

    @SimpleLog
    @PostMapping("")
    public Response add(@RequestParam String title, @RequestParam String description, @RequestParam Long userId) {
        newsService.add(title, description, userId);
        return Response.success();
    }
}
