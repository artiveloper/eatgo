package com.example.eatgo.interfaces;

import com.example.eatgo.domain.User;
import com.example.eatgo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public List<User> list() {
        return userService.getUsers();
    }

}
