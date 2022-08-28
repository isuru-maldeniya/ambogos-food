package com.example.ambigosdemo;

import com.example.ambigosdemo.platform.models.UserRoles;
import com.example.ambigosdemo.platform.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AmbigosDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmbigosDemoApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService service){
        return args -> {
            service.saveRole(UserRoles.USER);
            service.saveRole(UserRoles.ADMIN);
        };
    }

}
