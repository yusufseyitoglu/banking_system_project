package com.example.bankingBackend.responses;

import com.example.bankingBackend.models.User;

import lombok.Data;

@Data
public class RegisterSuccessResponse {
	private boolean success;
	private String message;
	private User user;
	public boolean isSuccess() {
		return success;
	}
}
