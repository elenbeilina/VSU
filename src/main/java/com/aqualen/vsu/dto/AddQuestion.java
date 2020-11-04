package com.aqualen.vsu.dto;

import com.aqualen.vsu.entity.Question;
import com.aqualen.vsu.entity.User;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class AddQuestion {
    long userId;
    String description;

    public static Question toEntity(AddQuestion from){
        return Question.builder()
                .user(User.builder().id(from.userId).build())
                .description(from.description)
                .dateCreated(LocalDate.now()).build();
    }
}
