package com.example.ambigosdemo.exceptions.models.handlers;

import com.example.ambigosdemo.exceptions.models.FieldException;
import com.example.ambigosdemo.exceptions.models.output.ExceptionOutputModel;
import com.example.ambigosdemo.exceptions.models.output.FieldExceptionOutputFormat;
import com.example.ambigosdemo.exceptions.types.UsernameNotAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UsernameNotAvailableException.class})
    public ResponseEntity<ExceptionOutputModel> getUsernameNotAvailableException(UsernameNotAvailableException e){
        ExceptionOutputModel exceptionOutputModel = new ExceptionOutputModel();
        List<FieldExceptionOutputFormat> fieldExceptions = new ArrayList<>();
        fieldExceptions.add(new FieldExceptionOutputFormat(e.getCode(), e.getMessage(), e.getFields()));
        exceptionOutputModel.setFieldExceptionOutputFormats(fieldExceptions);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionOutputModel);
    }
}
