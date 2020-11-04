package com.aqualen.vsu.controllers;

import com.aqualen.vsu.dto.AddNews;
import com.aqualen.vsu.log.SimpleLog;
import com.aqualen.vsu.entity.News;
import com.aqualen.vsu.services.NewsService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @SimpleLog
    @GetMapping("all")
    @ApiOperation(value = "Получить все новости")
    public ResponseEntity<List<News>> getNews() {
        return ResponseEntity.ok(newsService.getAll());
    }

    @SimpleLog
    @GetMapping
    public ResponseEntity<News> getOne(@RequestParam Long id) {
        return ResponseEntity.ok(newsService.getById(id));
    }

    @SimpleLog
    @PutMapping
    public void edit(@RequestBody News news) {
        newsService.update(news);
    }

    @SimpleLog
    @DeleteMapping
    public void delete(@RequestParam Long id) {
        newsService.delete(id);
    }

    @SimpleLog
    @PostMapping
    public ResponseEntity<News> add(@RequestBody AddNews addNews) {
        return ResponseEntity.ok(newsService.add(addNews));
    }
}
