package com.example.eatgo.interfaces;

import com.example.eatgo.domain.User;
import com.example.eatgo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

}