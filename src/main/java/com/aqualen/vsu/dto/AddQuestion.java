package com.aqualen.vsu.dto;

import com.aqualen.vsu.entity.Question;
import com.aqualen.vsu.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.aqualen.vsu.utils.UserUtils.getUserId;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddQuestion {
    @NotNull
    private String description;

    public static Question toEntity(AddQuestion from){
        return Question.builder()
                .user(User.builder().id(getUserId()).build())
                .description(from.description)
                .startDate(LocalDate.now()).build();
    }
}
