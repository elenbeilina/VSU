package com.aqualen.vsu.utils;

import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.entity.enums.UserRole;
import com.aqualen.vsu.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class Updater {

    private DepartmentService departmentService;
    private BCryptPasswordEncoder passwordEncoder;
    private static final String URL_DEFAULT_PICTURE = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRxO_i_Va1kmSnEuc79-CAgLhmmnyaSIBjeqXqhsuD3tpyFSD7Q";

    @Autowired
    Updater(DepartmentService departmentService){
        this.departmentService = departmentService;
        passwordEncoder = new BCryptPasswordEncoder();
    }

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
                    user.setStudentBookId(Long.parseLong(params.getFirst(k)));
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
}
