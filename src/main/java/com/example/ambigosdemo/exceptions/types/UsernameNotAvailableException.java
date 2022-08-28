package com.example.ambigosdemo.exceptions.types;

import com.example.ambigosdemo.exceptions.models.FieldException;

import java.util.List;

public class UsernameNotAvailableException extends FieldException {

    private UsernameNotAvailableException(String ... fields){
        initialize(fields);
    }

    public static UsernameNotAvailableException getUsernameNotAvailableException(String ... fields){
        return new UsernameNotAvailableException(fields);
    }

    public void initialize(String ...fields){
        super.addFields(fields);
        initializeMessage();
        initializeCode();
    }

    public void initializeCode(){
        super.setCode("username.not.available");
    }

    public void initializeMessage(){
        super.setMessage("Username not available");
    }

}
