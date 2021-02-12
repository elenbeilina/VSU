package com.aqualen.vsu.controllers;

import com.aqualen.vsu.dto.AddNews;
import com.aqualen.vsu.entity.News;
import com.aqualen.vsu.services.CustomUserDetailsService;
import com.aqualen.vsu.services.NewsService;
import lombok.SneakyThrows;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.aqualen.vsu.utils.JsonUtils.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(NewsController.class)
@AutoConfigureMockMvc(addFilters = false)
public class NewsControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private NewsService newsService;

    @Ignore
    @SneakyThrows
    @Test
    public void getNews() {
        when(newsService.add(any())).thenReturn(News.builder().build());
        mvc.perform(post("/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(AddNews.builder().build())))
                .andExpect(status().isOk());
    }
}