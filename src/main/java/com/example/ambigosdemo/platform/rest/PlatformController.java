package com.example.ambigosdemo.platform.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.ambigosdemo.platform.entities.User;
import com.example.ambigosdemo.platform.models.UserInputModel;
import com.example.ambigosdemo.platform.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class PlatformController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping()
    public ResponseEntity createUser(@RequestBody UserInputModel userInputModel){
        User user = userService.saveUser(userInputModel);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/food/users/").toUriString() + "/" + user.getId());
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody UserInputModel userInputModel, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = userInputModel.getUsername();
        String password = userInputModel.getPassword();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String accessToken = JWT.create().withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(request.getRequestURI().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refreshToken = JWT.create().withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000 * 24))
                .withIssuer(request.getRequestURI().toString())
                .sign(algorithm);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token",accessToken);
        tokens.put("refresh_token",refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
        return (ResponseEntity) response;

    }

    @GetMapping("/health")
    public ResponseEntity check(){
        System.out.println("in Health");
        return ResponseEntity.ok().build();
    }
}
