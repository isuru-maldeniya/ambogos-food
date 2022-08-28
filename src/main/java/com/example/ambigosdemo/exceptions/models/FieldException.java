package com.example.ambigosdemo.exceptions.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldException extends RuntimeException {
    private String code;
    private String message;
    private List<String> fields = new ArrayList<>();

    public FieldException(String message) {
        super(message);
    }

    public void addFields(String ... fields){
        for (int i=0; i<fields.length;i++){
            this.fields.add(fields[i]);
        }
    }
}
