package com.example.bankingBackend.exchangers;

import lombok.Data;

@Data
public class CollectApiResponse {

    private boolean success;
    private Result result;
}