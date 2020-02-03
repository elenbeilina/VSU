package com.aqualen.vsu.repository;

import com.aqualen.vsu.entity.RequestQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestQuestionRepository extends JpaRepository<RequestQuestion,Long> {
}
