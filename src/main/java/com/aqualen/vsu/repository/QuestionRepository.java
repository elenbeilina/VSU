package com.aqualen.vsu.repository;

import com.aqualen.vsu.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    List<Question> findAllByAnswerNotNull();
}
