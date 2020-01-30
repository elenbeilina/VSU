package com.aqualen.vsu.services.impl;

import com.aqualen.vsu.services.NewsService;
import com.aqualen.vsu.entity.News;
import com.aqualen.vsu.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    NewsRepository newsRepository;

    @Override
    public News addNews(News news) {
        return newsRepository.saveAndFlush(news);
    }

    @Override
    public void delete(long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public News getById(long id) {
        return newsRepository.getOne(id);
    }

    @Override
    public News updateNews(News news) {
        return newsRepository.saveAndFlush(news);
    }

    @Override
    public List<News> getAll() {
        return newsRepository.findAll();
    }

    @Override
    public List<News> getAll(int count) {
        List<News> news = newsRepository.findAll();
        if (news.size() > count)
            return news.subList(0, count);
        else
            return news;
    }
}
