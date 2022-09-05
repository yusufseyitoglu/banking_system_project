package com.example.bankingBackend.responses;

import lombok.Data;

@Data
public class RegisterErrorResponse {
	private boolean success;
	private String message;
}
