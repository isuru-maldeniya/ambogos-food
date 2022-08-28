package com.example.ambigosdemo.exceptions.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class GlobalException extends RuntimeException {
    private String code;
    private String message;
}
