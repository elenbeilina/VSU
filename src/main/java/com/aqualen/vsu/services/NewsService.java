package com.aqualen.vsu.services;

import com.aqualen.vsu.entity.News;

import java.util.List;

public interface NewsService {
    News addNews(News news);
    void delete(long id);
    News getById(long id);
    News updateNews(News news);
    List<News> getAll(int count);
    List<News> getAll();
}
