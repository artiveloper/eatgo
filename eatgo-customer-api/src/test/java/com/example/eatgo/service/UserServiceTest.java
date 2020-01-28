package com.example.eatgo.service;

import com.example.eatgo.domain.User;
import com.example.eatgo.domain.UserRepository;
import com.example.eatgo.exception.EmailExistedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    void registerUserWithExistedEmail() {
        String email = "tester@gmail.com";
        String name = "tester";
        String password = "password";

        User mockUser = User.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();

        doReturn(Optional.of(mockUser)).when(userRepository).findByEmail(email);

        Exception exception = assertThrows(
                EmailExistedException.class,
                () -> userService.registerUser(email, name, password));

        verify(userRepository, never()).save(any());
        assertEquals("Email is already registered : " + email, exception.getMessage());
    }

}