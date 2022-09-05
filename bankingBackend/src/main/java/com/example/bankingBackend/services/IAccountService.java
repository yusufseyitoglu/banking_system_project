package com.example.bankingBackend.services;

import java.util.List;

import com.example.bankingBackend.enums.AccountType;
import com.example.bankingBackend.models.Account;

public interface IAccountService {
	
	public Account create(int bank_id, AccountType type);
	
	
	public boolean remove(int id);
	
	public Account findById(int id);


	public Account addAmount(int id, double amount);
	
	public boolean transfer(int senderAccountId,int receiverAccountId,double amount);

    public List<Account> getAllAccounts();


	Account accountDetail(int id);


}
