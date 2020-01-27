package com.vsu.project.repository;

import com.vsu.project.entity.RequestQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestQuestionRepository extends JpaRepository<RequestQuestion,Long> {
}
