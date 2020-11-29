package com.aqualen.vsu.services;

import com.aqualen.vsu.config.jwt.CustomUser;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.exceptions.LoginProcessException;
import com.aqualen.vsu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public CustomUser loadUserByUsername(String username) {
        try {
            User user = userRepository.findByUsername(username);
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
            return new CustomUser(user.getUsername(), user.getPassword(), grantedAuthorities, user.getId());
        } catch (Exception e) {
            throw new LoginProcessException("Пользователя с таким именем не существует !");
        }
    }
}
