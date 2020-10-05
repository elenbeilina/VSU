package com.aqualen.vsu.logic;

import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.entity.enums.UserRole;
import com.aqualen.vsu.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class RatingLogic {
    @Autowired
    UserRepository userRepository;

    public List<User> getUsersList(int count) {
        List<User> users = userRepository.findAllByRole(UserRole.User);
        sortByRating(users);
        return users.size() > count ? users.subList(0, count) : users;
    }

    public List<User> getUsersList() {
        List<User> users = userRepository.findAllByRole(UserRole.User);
        sortByRating(users);
        return users;
    }

    private void sortByRating(List<User> users) {
        users.sort(Comparator.comparingLong(User::getRating)
                .reversed());
    }
}
