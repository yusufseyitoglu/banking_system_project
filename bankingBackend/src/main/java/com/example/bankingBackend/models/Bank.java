package com.example.bankingBackend.models;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("Bank")
@Data
public class Bank {
    private int id;
    private String name;
}