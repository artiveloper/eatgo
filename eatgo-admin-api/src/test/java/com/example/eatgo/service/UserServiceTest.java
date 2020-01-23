package com.example.eatgo.service;

import com.example.eatgo.domain.User;
import com.example.eatgo.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
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
        assertEquals(user.getEmail(), "tester@example.com");
    }

    @Test
    void addUser() {
        //given
        String email = "tester@example.com";
        String name = "tester";
        long level = 100L;

        User expectedUser = User.builder()
                .email(email)
                .name(name)
                .level(level)
                .build();

        doReturn(expectedUser).when(userRepository).save(any());

        //when
        User user = userService.addUser(email, name);

        //then
        assertEquals(expectedUser.getEmail(), user.getEmail());
        assertEquals(expectedUser.getName(), user.getName());
    }

    @Test
    void updateUser() {
        //given
        Long id = 1L;
        String email = "tester@example.com";
        String name = "update_tester";
        Long level = 100L;

        User expectedUser = User.builder()
                .email(email)
                .name("tester")
                .build();

        doReturn(Optional.of(expectedUser)).when(userRepository).findById(id);

        User user = userService.updateUser(id, email, name, level);

        //when
        verify(userRepository).findById(eq(id));
        assertEquals(name, user.getName());
        assertTrue(user.isAdmin());
    }

}