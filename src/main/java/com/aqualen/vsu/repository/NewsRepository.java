package com.aqualen.vsu.repository;

import com.aqualen.vsu.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
