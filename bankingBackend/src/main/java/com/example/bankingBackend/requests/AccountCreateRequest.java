package com.example.bankingBackend.requests;

import com.example.bankingBackend.enums.AccountType;

import lombok.Data;

@Data
public class AccountCreateRequest {
	private int bank_id;
    private AccountType type;
	
}
