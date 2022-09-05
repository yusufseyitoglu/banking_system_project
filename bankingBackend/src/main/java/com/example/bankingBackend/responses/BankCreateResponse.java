package com.example.bankingBackend.responses;

import com.example.bankingBackend.models.Bank;

import lombok.Data;

@Data
public class BankCreateResponse {
	private boolean success;
	private String message;
	private Bank bank;
	public boolean isSuccess() {
		return success;
	}
}
