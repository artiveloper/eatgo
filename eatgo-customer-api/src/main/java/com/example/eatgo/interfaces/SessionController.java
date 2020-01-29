package com.example.eatgo.interfaces;

import com.example.eatgo.domain.User;
import com.example.eatgo.dto.SessionRequestDto;
import com.example.eatgo.dto.SessionResponseDto;
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
public class SessionController {

    private final UserService userService;

    @PostMapping("/session")
    public ResponseEntity<?> create(@RequestBody SessionRequestDto resource) throws URISyntaxException {

        String email = resource.getEmail();
        String password = resource.getPassword();

        userService.authenticate(email, password);

        SessionResponseDto sessionResponseDto = SessionResponseDto.builder()
                .accessToken("ACCESSTOKEN")
                .build();

        String url = "/session";

        return ResponseEntity.created(new URI(url)).body(sessionResponseDto);
    }

}
