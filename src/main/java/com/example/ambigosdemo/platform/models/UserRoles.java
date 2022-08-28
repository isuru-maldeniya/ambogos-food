package com.example.ambigosdemo.platform.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRoles {
    ADMIN("1","ADMIN"),USER("2","USER");
    private final String id;
    private final String name;
}
