package com.aqualen.vsu.controllers;

import com.aqualen.vsu.config.SecurityConfig;
import com.aqualen.vsu.config.jwt.JwtFilter;
import com.aqualen.vsu.dto.AddNews;
import com.aqualen.vsu.entity.News;
import com.aqualen.vsu.services.NewsService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.aqualen.vsu.utils.JsonUtils.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = NewsController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {SecurityConfig.class, JwtFilter.class}))
class NewsControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private NewsService newsService;

    @SneakyThrows
    @Test
    @WithMockUser
    void getNews() {
        when(newsService.add(any())).thenReturn(News.builder().build());
        mvc.perform(post("/news")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(AddNews.builder().build())))
                .andExpect(status().isOk());
    }
}