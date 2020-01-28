package com.example.eatgo.interfaces;

import com.example.eatgo.domain.User;
import com.example.eatgo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody User resources) throws URISyntaxException {

        User user = userService.registerUser(resources.getEmail(), resources.getName(), resources.getPassword());

        String url = "/users/"+ user.getId();

        return ResponseEntity.created(new URI(url)).body("{}");
    }

}
