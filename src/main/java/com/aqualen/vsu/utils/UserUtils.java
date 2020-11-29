package com.aqualen.vsu.utils;

import com.aqualen.vsu.config.jwt.CustomUser;
import com.aqualen.vsu.exceptions.LoginProcessException;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

@UtilityClass
public class UserUtils {
    public static Long getUserId(){
        CustomUser user = getUser();

        if (Objects.isNull(user)){
            throw new LoginProcessException("Данное действие могут делать только зарегестрированные пользователи !");
        }

        return user.getUserId();
    }

    public static String getUsername(){
        CustomUser user = getUser();
        return Objects.isNull(user) ? null : user.getUsername();
    }

    private CustomUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication))
            return null;

        Object principal = authentication.getPrincipal();

        if ((principal instanceof CustomUser))
            return (CustomUser) principal;
        return null;
    }
}
