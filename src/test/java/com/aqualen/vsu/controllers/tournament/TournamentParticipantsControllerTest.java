package com.aqualen.vsu.controllers.tournament;

import com.aqualen.vsu.config.SecurityConfig;
import com.aqualen.vsu.config.jwt.JwtFilter;
import com.aqualen.vsu.services.ParticipantsService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = TournamentParticipantsController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {SecurityConfig.class, JwtFilter.class}))
class TournamentParticipantsControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ParticipantsService service;

    @Test
    @SneakyThrows
    @WithMockUser
    void deleteParticipant() {
        mvc.perform(delete("/tournament/participant")
                .with(csrf())
                .param("tournamentId", String.valueOf(1L)))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteParticipantFromTournament(any());
    }
}