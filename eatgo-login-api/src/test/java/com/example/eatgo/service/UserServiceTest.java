package com.example.eatgo.service;

import com.example.eatgo.domain.User;
import com.example.eatgo.domain.UserRepository;
import com.example.eatgo.exception.EmailNotExistedException;
import com.example.eatgo.exception.PasswordWrongException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void authenticate() throws Exception {
        //given
        String email = "tester@gmail.com";
        String name = "tester";
        String password = "password";

        User mockUser = User.builder()
                .email(email)
                .name(name)
                .password(passwordEncoder.encode(password))
                .build();

        doReturn(Optional.of(mockUser)).when(userRepository).findByEmail(email);
        doReturn(true).when(passwordEncoder).matches(any(), any());


        //when
        User user = userService.authenticate(email, password);

        //then
        assertEquals(email, user.getEmail());
    }

    @Test
    void authenticateWithNotExistEmail() throws Exception {
        //given
        String email = "tester@gmail.com";
        String name = "tester";
        String password = "password";

        User mockUser = User.builder()
                //.email(email)
                .name(name)
                .password(password)
                .build();

        //when
        //doReturn(Optional.of(mockUser)).when(userRepository).findByEmail(email);
        doReturn(true).when(passwordEncoder).matches(any(), any());

        //then
        assertThrows(
                EmailNotExistedException.class,
                () -> userService.authenticate(email, password));
    }

    @Test
    void authenticateWithWrongPassword() throws Exception {
        //given
        String email = "tester@gmail.com";
        String name = "tester";
        String password = "WrongPassword";

        User mockUser = User.builder()
                .email(email)
                .name(name)
                .password("Password")
                .build();

        //when
        doReturn(Optional.of(mockUser)).when(userRepository).findByEmail(email);

        assertThrows(
                PasswordWrongException.class,
                () -> userService.authenticate(email, password));
    }

}