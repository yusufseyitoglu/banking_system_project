package com.example.bankingBackend.responses;

import lombok.Data;

@Data
public class AccountDepositSuccessResponse {
	private boolean success;
	private String message;
	private double balance;
	public boolean isSuccess() {
		return success;
	}
}
