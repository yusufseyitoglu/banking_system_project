package com.example.bankingBackend.models;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("User")
@Data
public class User {

    private int id;
    private String username;
    private String email;
    private String password;
    private boolean enabled;
    private String authorities;

}