package com.example.bankingBackend.requests;

import lombok.Data;

@Data
public class AccountBalanceTransferRequest {
	private double amount;
	private int receiverAccountId;
}
