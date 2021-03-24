package com.aqualen.vsu.controllers;

import com.aqualen.vsu.config.SecurityConfig;
import com.aqualen.vsu.config.jwt.JwtFilter;
import com.aqualen.vsu.enums.TechnologyName;
import com.aqualen.vsu.logic.RatingLogic;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = RatingController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {SecurityConfig.class, JwtFilter.class}))
class RatingControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private RatingLogic logic;

    @Test
    @SneakyThrows
    @WithMockUser
    void getAll() {
        when(logic.getUsersList(any(), any())).thenReturn(Page.empty());
        mvc.perform(get("/rating")
                .with(csrf())
                .param("technologyName", TechnologyName.JAVA.name())
                .param("pageable", PageRequest.of(1, 1).toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }
}