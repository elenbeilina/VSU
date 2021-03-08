package com.aqualen.vsu.repository;

import com.aqualen.vsu.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswersRepository extends JpaRepository<Answer, Long> {
}
