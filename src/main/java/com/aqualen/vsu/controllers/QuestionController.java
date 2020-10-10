package com.aqualen.vsu.controllers;

import com.aqualen.vsu.aspects.SimpleLog;
import com.aqualen.vsu.entity.Question;
import com.aqualen.vsu.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @SimpleLog
    @GetMapping("all")
    public ResponseEntity<List<Question>> getAll() {
        return ResponseEntity.ok(questionService.getAll());
    }

    @SimpleLog
    @GetMapping
    public ResponseEntity<Question> getOne(@RequestParam Long id) {
        return ResponseEntity.ok(questionService.getById(id));
    }

    @SimpleLog
    @PutMapping
    public void edit(@RequestBody Question question) {
        questionService.update(question);
    }

    @SimpleLog
    @DeleteMapping
    public void delete(@RequestParam Long id) {
        questionService.delete(id);
    }

    @SimpleLog
    @PostMapping
    public ResponseEntity<Question> add(@RequestBody Question question) {
        return ResponseEntity.ok(questionService.update(question));
    }
}
