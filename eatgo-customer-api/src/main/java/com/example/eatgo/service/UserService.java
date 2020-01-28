package com.example.eatgo.service;

import com.example.eatgo.domain.User;
import com.example.eatgo.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;


    public User registerUser(String email, String name, String password) {
        User user = User.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();

        return userRepository.save(user);
    }

}
