package com.example.ambigosdemo.exceptions.models.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldExceptionOutputFormat {
    private String code;
    private String message;
    private List<String> fields = new ArrayList<>();
}
