package com.aqualen.vsu.services;


import com.aqualen.vsu.entity.enums.UserRole;
import com.aqualen.vsu.repository.UserRepository;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.utils.Updater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    Updater updater;

    public User addUser(MultiValueMap<String, String> map) {
        User user = updater.updateUser(new User(), map);
        return userRepository.saveAndFlush(user);
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public User getById(long id) {
        return userRepository.getOne(id);
    }

    public User updateUser(User user, MultiValueMap<String, String> map) {
        user = updater.updateUser(user, map);
        return userRepository.saveAndFlush(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public List<User> getUsersByRole(UserRole role) {
        List<User> users = userRepository.findAllByRole(role);
        users.sort((a,b) -> {
            return a.getRating() > b.getRating() ? -1 : 1;
        });
        return users;
    }

    public List<User> getUsersByRole(UserRole role, int count) {
        List<User> users = userRepository.findAllByRole(role);
        users.sort((a,b) -> a.getRating() > b.getRating() ? -1 : 1);
        if (users.size() > count)
            return users.subList(0,count);
        else
            return users;
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
