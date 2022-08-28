package com.example.ambigosdemo.exceptions.models.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExceptionOutputModel {
    private List<GlobalExceptionOutputFormat> globalExceptionOutputFormats;
    private List<FieldExceptionOutputFormat> fieldExceptionOutputFormats;
}
