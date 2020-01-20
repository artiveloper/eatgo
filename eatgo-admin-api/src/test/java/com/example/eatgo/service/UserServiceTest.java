package com.example.eatgo.service;

import com.example.eatgo.domain.User;
import com.example.eatgo.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void getUsers() throws Exception {
        List<User> expectedUsers = Arrays.asList(
                User.builder()
                        .email("tester@example.com")
                        .name("tester")
                        .level(100L)
                        .build()
        );

        doReturn(expectedUsers)
                .when(userRepository).findAll();

        List<User> users = userService.getUsers();

        User user = users.get(0);
        assertEquals(user.getName(), "tester");
    }

}