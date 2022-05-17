package com.glasiem.service;

import com.glasiem.entity.UserEntity;
import com.glasiem.model.RolesEnum;
import com.glasiem.model.User;
import com.glasiem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    public UserRepo getUserRepo() {
        return userRepo;
    }

    public String registerNewAccount(User user) {
        if (userRepo.findByUsername(user.getUsername()) != null){
            return "Користувач з логіном " + user.getUsername() + " вже існує.";
        }

        if (user.getPassword().length() < 6){
            return "Пароль повинен містити мінімум 6 символів";
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setActive(true);
        userEntity.setRoles(Collections.singleton(RolesEnum.ROLE_USER));
        userRepo.save(userEntity);
        return null;
    }

}