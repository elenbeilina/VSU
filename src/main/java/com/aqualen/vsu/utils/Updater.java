package com.aqualen.vsu.utils;

import com.aqualen.vsu.entity.News;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.entity.enums.UserRole;
import com.aqualen.vsu.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class Updater {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final String URL_DEFAULT_PICTURE = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRxO_i_Va1kmSnEuc79-CAgLhmmnyaSIBjeqXqhsuD3tpyFSD7Q";

    public User updateUser(User user, MultiValueMap<String, String> params){
        params.forEach((k, v)->{
            switch (k){
                case "department":
                    user.setDepartment(
                            departmentService.getById(
                                    Long.parseLong(params.getFirst(k))
                            )
                    );
                    break;
                case "firstName":
                    user.setFirstName(params.getFirst(k));
                    break;
                case "secondName":
                    user.setSecondName(params.getFirst(k));
                    break;
                case "password":
                    user.setPassword(passwordEncoder.encode(params.getFirst(k)));
                    break;
                case "studentBookId":
                    user.setStudentBookId(params.getFirst(k));
                    break;
                case "username":
                    user.setUsername(params.getFirst(k));
                    break;
                case "role":
                    user.setRole(UserRole.valueOf(params.getFirst(k)));
                    break;
            }
        });
        if (user.getPicture() == null){
            user.setPicture(URL_DEFAULT_PICTURE);
        }
        return user;
    }

    public News updateNews(News news, MultiValueMap<String, String> params){
        params.forEach((k, v)->{
            switch (k) {
                case "title":
                    news.setTitle(params.getFirst(k));
                    break;
                case "description":
                    news.setDescription(params.getFirst(k));
                    break;
            }
        });
        return news;
    }
}
