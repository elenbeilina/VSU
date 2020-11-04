package com.aqualen.vsu.dto;

import com.aqualen.vsu.entity.News;
import com.aqualen.vsu.entity.User;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
@Builder
public class AddNews {
    @NotNull
    long userId;

    @NotNull
    String title;

    @NotNull
    String description;

    public static News toEntity(AddNews from){
        return News.builder()
                .title(from.title)
                .description(from.description)
                .user(User.builder().id(from.userId).build())
                .dateCreated(LocalDate.now()).build();
    }
}
