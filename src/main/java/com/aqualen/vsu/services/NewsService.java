package com.aqualen.vsu.services;

import com.aqualen.vsu.entity.News;
import com.aqualen.vsu.repository.NewsRepository;
import com.aqualen.vsu.utils.Updater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;

@Service
@Slf4j
public class NewsService {

    @Autowired
    NewsRepository newsRepository;
    @Autowired
    Updater updater;
    @Autowired
    UserService userService;

    public News addNews(MultiValueMap<String, String> map, Principal principal) {
        News news =  new News();
        news = updater.updateNews(news, map);
        news.setDateCreated(new Timestamp(System.currentTimeMillis()));
        news.setUser(userService.findByUsername(principal.getName()));
        return newsRepository.saveAndFlush(news);
    }

    public void delete(long id) {
        newsRepository.deleteById(id);
    }

    public News getById(long id) {
        return newsRepository.getOne(id);
    }

    public News updateNews(News news) {
        return newsRepository.saveAndFlush(news);
    }

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
}
