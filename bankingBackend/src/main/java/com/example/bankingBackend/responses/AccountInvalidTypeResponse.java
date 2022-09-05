package com.example.bankingBackend.responses;

import lombok.Data;

@Data
public class AccountInvalidTypeResponse {
	private boolean success;
	private String message;
	public boolean isSuccess() {
		return success;
	}
}
