package com.example.ambigosdemo.platform.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInputModel {
    private String username;
    private String email;
    private String password;
    private List<UserRoles> roles;
}
