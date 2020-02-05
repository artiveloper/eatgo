package com.example.eatgo.interfaces;

import com.example.eatgo.domain.User;
import com.example.eatgo.dto.SessionRequestDto;
import com.example.eatgo.dto.SessionResponseDto;
import com.example.eatgo.service.UserService;
import com.example.eatgo.utils.JwtUtil;
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
    private final JwtUtil jwtUtil;

    @PostMapping("/session")
    public ResponseEntity<?> create(@RequestBody SessionRequestDto resource) throws URISyntaxException {

        String email = resource.getEmail();
        String password = resource.getPassword();

        User user = userService.authenticate(email, password);

        System.out.println("====" + user.getRestaurantId());

        String accessToken = jwtUtil.createToken(
                user.getId(),
                user.getName(),
                user.isRestaurantOwner() ? user.getRestaurantId() : null
        );

        SessionResponseDto sessionResponseDto = SessionResponseDto.builder()
                .accessToken(accessToken)
                .build();

        String url = "/session";

        return ResponseEntity.created(new URI(url)).body(sessionResponseDto);
    }

}
