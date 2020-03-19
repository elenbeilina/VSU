package com.aqualen.vsu.services;

import com.aqualen.vsu.entity.Question;
import com.aqualen.vsu.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class QuestionService {

    @Autowired
    private QuestionRepository requestQuestionRepository;

    public Question addQuestion(Question requestQuestion) {
        return requestQuestionRepository.saveAndFlush(requestQuestion);
    }

    public void delete(long id) {
        requestQuestionRepository.deleteById(id);
    }

    public Question getById(long id) {
        return requestQuestionRepository.getOne(id);
    }

    public Question updateQuestion(Question requestQuestion) {
        return requestQuestionRepository.saveAndFlush(requestQuestion);
    }

    public List<Question> getAll(int count) {
        List<Question> list = requestQuestionRepository.findAll();
        return list.size() > count ? list.subList(0, count) : list;
    }

    public List<Question> getAll() {
        return requestQuestionRepository.findAll();
    }
}
