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
    private QuestionRepository questionRepository;

    public List<Question> getAll() {
        return questionRepository.findAll();
    }

    public List<Question> getAll(int count) {
        List<Question> list = questionRepository.findAll();
        return list.size() > count ? list.subList(0, count) : list;
    }

    public Question getById(Long id) {
        return questionRepository.getOne(id);
    }

    public Question updateQuestion(Question requestQuestion) {
        return questionRepository.saveAndFlush(requestQuestion);
    }

    public void delete(Long id) {
        questionRepository.deleteById(id);
    }
}
