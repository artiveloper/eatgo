package com.example.eatgo.interfaces;

import com.example.eatgo.domain.User;
import com.example.eatgo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public List<User> list() {
        return userService.getUsers();
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody User resource) throws URISyntaxException {
        User user = userService.addUser(resource.getEmail(), resource.getName());

        String url = "/users/" + user.getId();

        return ResponseEntity.created(new URI(url)).body("{}");
    }

}
