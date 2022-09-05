package com.example.bankingBackend.responses;

import com.example.bankingBackend.models.Account;
import lombok.Data;

@Data
public class AccountCreateSuccessResponse {
	private boolean success;
	private String message;
	private Account account;
	
	public boolean isSuccess() {
		return success;
	}
}
