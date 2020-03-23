package com.aqualen.vsu.services;

import com.aqualen.vsu.entity.News;
import com.aqualen.vsu.repository.NewsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NewsService {

    @Autowired
    NewsRepository newsRepository;

    public List<News> getAll() {
        return newsRepository.findAll();
    }

    public List<News> getAll(int count) {
        List<News> news = newsRepository.findAll();
        if (news.size() > count)
            return news.subList(0, count);
        else
            return news;
    }

    public News getById(long id) {
        return newsRepository.getOne(id);
    }

    public void update(News news) {
        newsRepository.saveAndFlush(news);
    }

    public void delete(Long id) {
        newsRepository.deleteById(id);
    }
}
