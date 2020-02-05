package com.example.eatgo.interfaces;

import com.example.eatgo.domain.User;
import com.example.eatgo.exception.EmailNotExistedException;
import com.example.eatgo.exception.PasswordWrongException;
import com.example.eatgo.service.UserService;
import com.example.eatgo.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SessionController.class)
class SessionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void create() throws Exception {
        //given
        Long id = 1L;
        Long level = 1L;
        String email = "tester@gmail.com";
        String name = "tester";
        String password = "password";

        User mockUser = User.builder()
                .id(id)
                .email(email)
                .name(name)
                .password(password)
                .level(level)
                .build();

        doReturn(mockUser).when(userService).authenticate(email, password);
        doReturn("header.payload.signature").when(jwtUtil).createToken(id, name, null);

        //when, then
        String requestBody = "{\"email\": \"tester@gmail.com\", \"password\": \"password\"}";

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/session"))
                .andExpect(content().string("{\"accessToken\":\"header.payload.signature\"}"));

        verify(userService).authenticate(eq(email), eq(password));
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

    @Test
    void createRestaurantOwner() throws Exception {
        //given
        Long id = 1L;
        Long level = 50L;
        String email = "owner@gmail.com";
        String name = "owner";
        String password = "password";

        Long restaurantId = 1L;

        User mockUser = User.builder()
                .id(id)
                .email(email)
                .name(name)
                .password(password)
                .restaurantId(restaurantId)
                .level(level)
                .build();

        doReturn(mockUser).when(userService).authenticate(email, password);
        doReturn("header.payload.signature").when(jwtUtil).createToken(id, name, restaurantId);

        //when, then
        String requestBody = "{\"email\": \"owner@gmail.com\", \"password\": \"password\"}";

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/session"))
                .andExpect(content().string("{\"accessToken\":\"header.payload.signature\"}"));

        assertTrue(mockUser.isRestaurantOwner());
        verify(userService).authenticate(eq(email), eq(password));
    }

}