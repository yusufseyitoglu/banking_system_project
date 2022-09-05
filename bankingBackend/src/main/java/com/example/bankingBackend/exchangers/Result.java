package com.example.bankingBackend.exchangers;

import java.util.List;

@lombok.Data
public class Result {

    private String base;
    private String lastupdate;
    private List<Data> data;
}
