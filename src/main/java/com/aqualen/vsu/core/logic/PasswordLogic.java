package com.aqualen.vsu.core.logic;

import com.aqualen.vsu.core.entity.User;
import com.aqualen.vsu.core.exceptions.PasswordException;
import com.aqualen.vsu.core.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordLogic {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String OLD_PASSWORD_ERROR_MESSAGE = "Не правильно введен старый пароль";
    private static final String NEW_PASSWORD_ERROR_MESSAGE = "Введенные пароли не совпадают";

    public void encodePassword(User user) {
        String password = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(password);
    }

    @SneakyThrows
    public void updatePassword(String userName, String old, String newOne, String newTwo) {
        User user = userRepository.findByUsername(userName);

        checkOldPassword(old, user.getPassword());
        checkNewPassword(newOne, newTwo);

        user.setPassword(bCryptPasswordEncoder.encode(newOne));
        userRepository.saveAndFlush(user);
    }

    @SneakyThrows
    private void checkOldPassword(String userInput, String actual) {
        if (!bCryptPasswordEncoder.matches(userInput, actual)) {
            throw new PasswordException(OLD_PASSWORD_ERROR_MESSAGE);
        }
    }

    @SneakyThrows
    private void checkNewPassword(String newOne, String newTwo) {
        if (!newOne.equals(newTwo)) {
            throw new PasswordException(NEW_PASSWORD_ERROR_MESSAGE);
        }
    }
}
