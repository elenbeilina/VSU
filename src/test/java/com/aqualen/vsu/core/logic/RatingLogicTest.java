package com.aqualen.vsu.core.logic;

import com.aqualen.vsu.core.entity.User;
import com.aqualen.vsu.core.entity.enums.UserRole;
import com.aqualen.vsu.core.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class RatingLogicTest {
    List<User> results;
    List<User> filteredUsers;

    @BeforeEach
    public void init() {
        User one = new User(1, UserRole.User, "123", "aqua", "elena", "beil", "123", 2);
        User two = new User(1, UserRole.Sponsor, "123", "1", "1", "1", "123", 1);
        User three = new User(1, UserRole.User, "123", "aqua", "elena", "beil", "123", 2);
        User fore =  new User(1, UserRole.User, "123", "aqua", "elena", "beil", "123", 2);
        User five = new User(1, UserRole.User, "123", "aqua", "elena", "beil", "123", 2);
        User six = new User(1, UserRole.User, "123", "aqua", "elena", "beil", "123", 2);
        results = Arrays.asList(one, two, three, fore, five, six);

        Predicate<User> byRole = user -> user.getRole().equals(UserRole.User);
        filteredUsers = results.stream().filter(byRole)
                .collect(Collectors.toList());
    }

    @Test
    void getUsersList() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        Mockito.when(userRepository.findAllByRole(UserRole.User)).thenReturn(filteredUsers);

        RatingLogic ratingLogic = new RatingLogic(userRepository);
        List<User> result = ratingLogic.getUsersList();

        Assertions.assertEquals(result, filteredUsers);
    }

    @ParameterizedTest(name = "count={0}, result={1}")
    @CsvSource({"2,2", "5,5", "10,5"})
    void getUsersList(int count, int expected) {
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        Mockito.when(userRepository.findAllByRole(UserRole.User)).thenReturn(filteredUsers);

        RatingLogic ratingLogic = new RatingLogic(userRepository);
        List<User> result = ratingLogic.getUsersList(count);

        Assertions.assertEquals(expected, result.size());
    }
}