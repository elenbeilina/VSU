package com.aqualen.vsu.services;

import com.aqualen.vsu.dto.ParticipantResponse;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.logic.RatingLogic;
import com.aqualen.vsu.repository.ParticipantsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ParticipantsServiceTest {

    @Mock
    private ParticipantsRepository repository;
    @Mock
    private RatingLogic logic;
    @InjectMocks
    private ParticipantsService service;

    @Test
    void gradeParticipants() {
        service.gradeParticipants(1L, Collections
                .singletonList(ParticipantResponse.builder()
                        .grade(1)
                        .user(User.builder().id(1).build()).build()));

        verify(repository,times(1)).saveAll(anyIterable());
        verify(logic,times(1)).rateUsers(anyList());
    }
}