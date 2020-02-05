package com.aqualen.vsu.services;

import com.aqualen.vsu.entity.RequestQuestion;
import com.aqualen.vsu.repository.RequestQuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RequestQuestionService {

    @Autowired
    private RequestQuestionRepository requestQuestionRepository;

    public RequestQuestion addRequestQuestion(RequestQuestion requestQuestion) {
        return requestQuestionRepository.saveAndFlush(requestQuestion);
    }

    public void delete(long id) {
        requestQuestionRepository.deleteById(id);
    }

    public RequestQuestion getById(long id) {
        return requestQuestionRepository.getOne(id);
    }

    public RequestQuestion updateRequestQuestion(RequestQuestion requestQuestion) {
        return requestQuestionRepository.saveAndFlush(requestQuestion);
    }

    public List<RequestQuestion> getAll(int count) {
        List<RequestQuestion> list = requestQuestionRepository.findAll();
        return list.size() > count ? list.subList(0, count) : list;
    }

    public List<RequestQuestion> getAll() {
        return requestQuestionRepository.findAll();
    }
}
