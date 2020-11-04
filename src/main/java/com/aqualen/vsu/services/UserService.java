package com.aqualen.vsu.services;

import com.aqualen.vsu.dto.RegistrationResponse;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.enums.UserRole;
import com.aqualen.vsu.exceptions.PasswordException;
import com.aqualen.vsu.logic.PasswordLogic;
import com.aqualen.vsu.repository.UserRepository;
import com.aqualen.vsu.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.aqualen.vsu.dto.RegistrationResponse.toEntity;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String WRONG_USERNAME_PASSWORD_ERROR_MESSAGE = "Неправильно введен логин или пароль!";

    private final UserRepository userRepository;
    private final PasswordLogic passwordLogic;

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public User getById(long id) {
        return userRepository.getOne(id);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findAllByRole(role);
    }

    public User getUser(){
        return UserUtils.getUser();
    }

    public void update(User user) {
        userRepository.saveAndFlush(user);
    }

    public User add(RegistrationResponse registrationForm) {
        User user = toEntity(registrationForm);

        passwordLogic.encodePassword(user);

        return userRepository.saveAndFlush(user);
    }

    @SneakyThrows
    public User findByUsernameAndPassword(String username, String password) {
        User user = findByUsername(username);
        if (user != null) {
            if (passwordLogic.checkMatches(password, user)) {
                return user;
            }
        }
        throw new PasswordException(WRONG_USERNAME_PASSWORD_ERROR_MESSAGE);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
