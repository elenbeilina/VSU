package com.vsu.project.services.impl;

import com.vsu.project.entity.User;
import com.vsu.project.entity.enums.UserRole;
import com.vsu.project.repository.UserRepository;
import com.vsu.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User addUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getById(long id) {
        return userRepository.getOne(id);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByRole(UserRole role) {
        List<User> users = userRepository.findAllByRole(role);
        users.sort((a,b) -> {
            return a.getRating() > b.getRating() ? -1 : 1;
        });
        return users;
    }

    @Override
    public List<User> getUsersByRole(UserRole role, int count) {
        List<User> users = userRepository.findAllByRole(role);
        users.sort((a,b) -> a.getRating() > b.getRating() ? -1 : 1);
        if (users.size() > count)
            return users.subList(0,count);
        else
            return users;
    }

    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
