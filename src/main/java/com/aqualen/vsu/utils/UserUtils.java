package com.aqualen.vsu.utils;

import com.aqualen.vsu.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class UserUtils {
    public static User getUser() {
        if (SecurityContextHolder.getContext().getAuthentication() == null)
            return null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ((principal instanceof User))
            return (User) principal;
        return null;
    }
}
