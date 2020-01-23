package com.example.eatgo.interfaces;

import com.example.eatgo.domain.User;
import com.example.eatgo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void list() throws Exception {
        List<User> expectedUsers = Arrays.asList(
                User.builder()
                        .email("tester@example.com")
                        .name("tester")
                        .level(100L)
                        .build()
        );

        doReturn(expectedUsers)
                .when(userService).getUsers();

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("tester@example.com")
                ))
                .andExpect(content().string(
                        containsString("tester")
                ))
                .andExpect(content().string(
                        containsString("100")
                ));
    }

    @Test
    void create() throws Exception {
        //given
        String email = "admin@example.com";
        String name = "admin";

        User expectedUser = User.builder()
                .email(email)
                .name(name)
                .build();

        doReturn(expectedUser).when(userService).addUser(email, name);

        //when
        String requestBody = "{\"email\": \"admin@example.com\", \"name\": \"admin\"}";

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated());

        //then
        verify(userService).addUser(email, name);
    }

    @Test
    void update() throws Exception {
        //given
        Long id = 1L;
        String email = "admin@example.com";
        String name = "admin";
        Long level = 100L;

        //when
        String requestBody = "{\"id\": 1, \"email\": \"admin@example.com\", \"name\": \"admin\", \"level\": 100}";

        mvc.perform(patch("/users/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());

        //then
        verify(userService).updateUser(eq(id), eq(email), eq(name), eq(level));
    }

}