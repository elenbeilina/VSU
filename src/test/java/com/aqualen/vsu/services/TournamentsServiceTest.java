package com.aqualen.vsu.services;

import com.aqualen.vsu.entity.Tournament;
import com.aqualen.vsu.repository.TournamentRepository;
import com.aqualen.vsu.utils.UserUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TournamentsServiceTest {

    @Mock
    private TournamentRepository repository;
    @InjectMocks
    private TournamentsService service;

    @Test
    void getTournamentsForSponsor() {
        try (MockedStatic<UserUtils> userUtils = Mockito.mockStatic(UserUtils.class)) {
            userUtils.when(UserUtils::getUserId)
                    .thenReturn(1L);
            when(repository.findBySponsorId(anyLong()))
                    .thenReturn(Collections.singletonList(Tournament.builder().id(1L).build()));

            List<Tournament> result = service.getTournamentsForSponsor(null);

            assertThat(result).hasSize(1);
        }
    }
}