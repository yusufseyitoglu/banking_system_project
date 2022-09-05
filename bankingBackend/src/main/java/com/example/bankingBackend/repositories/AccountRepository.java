package com.example.bankingBackend.repositories;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.example.bankingBackend.models.Account;

@Mapper
public interface AccountRepository {

	
	public void createAccount(Account account);
	
    public int removeAccount(int id);
    
    public Account findById(int id);
    
	public Account findByUserId(long number);
	
	public Account getAccount(int id);

	public void updateBalance(Account account);
	
	public List<Account> getAllAccounts();
	
    public void transferMoney(double balance, int accountId, Date date);



}
