package com.aqualen.vsu.dto;

import com.aqualen.vsu.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParticipantResponse {
    private User user;
    private int grade;
    private String task;
}
