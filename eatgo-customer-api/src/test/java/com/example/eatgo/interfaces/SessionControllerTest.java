package com.example.eatgo.interfaces;

import com.example.eatgo.exception.EmailNotExistedException;
import com.example.eatgo.exception.PasswordWrongException;
import com.example.eatgo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SessionController.class)
class SessionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    UserService userService;

    @Test
    void create() throws Exception {
        //when, then
        String requestBody = "{\"email\": \"tester@gmail.com\", \"password\": \"password\"}";

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/session"))
                .andExpect(content().string("{\"accessToken\":\"ACCESSTOKEN\"}"));

        verify(userService).authenticate(eq("tester@gmail.com"), eq("password"));
    }

    @Test
    void createWithWrongPassword() throws Exception {
        //given
        doThrow(PasswordWrongException.class).when(userService).authenticate("tester@gmail.com", "x");

        //when, then
        String requestBody = "{\"email\": \"tester@gmail.com\", \"password\": \"x\"}";

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("tester@gmail.com"), eq("x"));
    }

    @Test
    void createWithNotExistEmail() throws Exception {
        //given
        doThrow(EmailNotExistedException.class).when(userService).authenticate("x@gmail.com", "x");

        //when, then
        String requestBody = "{\"email\": \"x@gmail.com\", \"password\": \"x\"}";

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("x@gmail.com"), eq("x"));
    }

}