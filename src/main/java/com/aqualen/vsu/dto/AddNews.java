package com.aqualen.vsu.dto;

import com.aqualen.vsu.entity.News;
import com.aqualen.vsu.entity.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.aqualen.vsu.utils.UserUtils.getUserId;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddNews {
    @NotNull
    private String title;

    @NotNull
    private String description;

    public static News toEntity(AddNews from, long userId){
        return News.builder()
                .title(from.title)
                .description(from.description)
                .user(User.builder().id(userId).build())
                .dateCreated(LocalDate.now()).build();
    }
}
