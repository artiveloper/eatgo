package com.example.eatgo.service;

import com.example.eatgo.domain.User;
import com.example.eatgo.domain.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void registerUser() {
        String email = "tester@gmail.com";
        String name = "tester";
        String password = "password";

        userService.registerUser(email, name, password);

        verify(userRepository).save(any());
    }

}