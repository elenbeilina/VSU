package com.aqualen.vsu.services;

import com.aqualen.vsu.entity.Question;
import com.aqualen.vsu.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

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

    public Question update(Question question) {
        return questionRepository.saveAndFlush(question);
    }

    public void delete(Long id) {
        questionRepository.deleteById(id);
    }
}
