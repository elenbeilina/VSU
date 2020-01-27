package com.vsu.project.services;

import com.vsu.project.entity.RequestQuestion;

import java.util.List;

public interface RequestQuestionService {
    RequestQuestion addRequestQuestion(RequestQuestion requestQuestion);
    void delete(long id);
    RequestQuestion getById(long id);
    RequestQuestion updateRequestQuestion(RequestQuestion requestQuestion);
    List<RequestQuestion> getAll(int count);
    List<RequestQuestion> getAll();
}
