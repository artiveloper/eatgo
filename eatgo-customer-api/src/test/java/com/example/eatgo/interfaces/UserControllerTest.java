package com.example.eatgo.interfaces;

import com.example.eatgo.domain.User;
import com.example.eatgo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    UserService userService;

    @Test
    void create() throws Exception {
        //given
        Long id = 1L;
        String email = "tester@gmail.com";
        String name = "tester";
        String password = "password";

        User expectedUser = User.builder()
                .id(id)
                .email(email)
                .name(name)
                .password(password)
                .build();

        doReturn(expectedUser).when(userService).registerUser(email, name, password);

        //when, then
        String requestBody = "{\"email\": \"tester@gmail.com\", \"name\": \"tester\", \"password\": \"password\"}";
        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/users/" + id));

        verify(userService).registerUser(eq(email), eq(name), eq(password));
    }

}