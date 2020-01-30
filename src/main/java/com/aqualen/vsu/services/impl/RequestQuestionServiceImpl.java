package com.aqualen.vsu.services.impl;

import com.aqualen.vsu.entity.RequestQuestion;
import com.aqualen.vsu.services.RequestQuestionService;
import com.aqualen.vsu.repository.RequestQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RequestQuestionServiceImpl implements RequestQuestionService {

    @Autowired
    private RequestQuestionRepository requestQuestionRepository;

    @Override
    public RequestQuestion addRequestQuestion(RequestQuestion requestQuestion) {
        return requestQuestionRepository.saveAndFlush(requestQuestion);
    }

    @Override
    public void delete(long id) {
        requestQuestionRepository.deleteById(id);
    }

    @Override
    public RequestQuestion getById(long id) {
        return requestQuestionRepository.getOne(id);
    }

    @Override
    public RequestQuestion updateRequestQuestion(RequestQuestion requestQuestion) {
        return requestQuestionRepository.saveAndFlush(requestQuestion);
    }

    @Override
    public List<RequestQuestion> getAll(int count) {
        List<RequestQuestion> list = requestQuestionRepository.findAll();
        return list.size() > count ? list.subList(0, count) : list;
    }

    @Override
    public List<RequestQuestion> getAll() {
        return requestQuestionRepository.findAll();
    }
}
