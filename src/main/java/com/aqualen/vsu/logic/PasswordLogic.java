package com.aqualen.vsu.logic;

import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.exceptions.PasswordException;
import com.aqualen.vsu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordLogic {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String OLD_PASSWORD_ERROR_MESSAGE = "Не правильно введен старый пароль";
    private static final String NEW_PASSWORD_ERROR_MESSAGE = "Введенные пароли не совпадают";

    public void encodePassword(User user) {
        String password = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(password);
    }

    public boolean checkMatches(String password, User user) {
        return bCryptPasswordEncoder.matches(password, user.getPassword());
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
