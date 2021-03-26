package com.aqualen.vsu.serialization;

import com.aqualen.vsu.entity.RatingByTechnology;
import com.aqualen.vsu.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static com.aqualen.vsu.enums.TechnologyName.JAVA;
import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class RatingSerializationTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void mappingTest() {
        RatingByTechnology rating = RatingByTechnology.builder()
                .user(new User())
                .key(RatingByTechnology.Key.builder().userId(1L).technology(JAVA).build())
                .deviation(21.0)
                .mean(2.0).build();

        String result = objectMapper.writeValueAsString(rating);

        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo("{\"key\":{\"userId\":1,\"technology\":\"JAVA\"},\"mean\":2.0,\"deviation\":21.0,\"rating\":null}");
    }
}
