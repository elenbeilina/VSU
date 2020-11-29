package com.aqualen.vsu.controllers.question;

import com.aqualen.vsu.enums.UserRole;
import com.aqualen.vsu.log.SimpleLog;
import com.aqualen.vsu.entity.Question;
import com.aqualen.vsu.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("questions")
@RequiredArgsConstructor
public class QuestionsController {

    private final QuestionService questionService;

    @SimpleLog
    @GetMapping
    public ResponseEntity<List<Question>> getAll(@RequestParam UserRole role) {
        return ResponseEntity.ok(questionService.getByRole(role));
    }
}
