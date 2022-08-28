package com.example.ambigosdemo.platform.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlgorithmConfig {
    @Value("${food-app.token.secret}")
    private String secret;

    @Bean
    public Algorithm getAlgorithm(){
        return Algorithm.HMAC256(secret.getBytes());
    }
}
