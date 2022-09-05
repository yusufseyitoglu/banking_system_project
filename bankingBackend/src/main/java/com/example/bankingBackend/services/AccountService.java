package com.example.bankingBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.example.bankingBackend.enums.AccountType;
import com.example.bankingBackend.exchangers.IExchange;
import com.example.bankingBackend.models.Account;
import com.example.bankingBackend.models.User;
import com.example.bankingBackend.repositories.AccountRepository;
import com.example.bankingBackend.repositories.BankRepository;
import com.example.bankingBackend.repositories.UserRepository;

@Component
public class AccountService implements IAccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private BankRepository bankRepository;
    
    @Autowired
    private IExchange exchangeService;
    
	@Override
    public Account create(int bankId, AccountType type) {
    	
    	if(type.equals("USD") || type.equals("TRY") || type.equals("GAU")) {
			User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user=(User) this.userRepository.findByUsername(authUser.getUsername());
	        long accountNumber = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
			Account account=new Account();
			account.setUser_id(user.getId());
			account.setBank_id(bankId);
			account.setNumber(accountNumber);
			account.setType(type);
			account.setBalance(0);
	        account.setCreation_date(new Date());
	        account.setLast_update_date(new Date());
	        account.setDeleted(false);

			this.accountRepository.createAccount(account);
			return this.accountRepository.findByUserId(account.getNumber());
    	}	
    
		return null;
    }
	@Override
    public boolean remove(int id) {
        int recordNumber = this.accountRepository.removeAccount(id);
        if(recordNumber==1){
            return true;
        }else{
            return false;
        }
    }

	

	@Override
	public Account addAmount(int id, double amount) {
        Account account = this.accountRepository.findById(id);
		if(account!=null) {
			account.setBalance(account.getBalance() + amount);
			account.setLast_update_date(new Timestamp(System.currentTimeMillis()));
			this.accountRepository.updateBalance(account);
			return account;
		}
		return account;
	}
	
    @Transactional
	@Override
	public boolean transfer(int senderAccountId,int receiverAccountId,double amount) {
    	Account senderAccount = this.accountRepository.findById(senderAccountId);
        Account receiverAccount = this.accountRepository.findById(receiverAccountId);
        double transferAmount = amount;
        var date = new Date();
        if(senderAccount.getBalance()< amount && amount<=0){
            return false;
        }

        if(!senderAccount.getType().equals(receiverAccount.getType())){
            transferAmount = this.exchangeService.exchange(amount,senderAccount.getType(),receiverAccount.getType());
        }

        if(!senderAccount.getBank().equals(receiverAccount.getBank())){
            if(senderAccount.getType().equals(AccountType.TRY)){
                amount = amount + 3;
            }
            if(senderAccount.getType().equals(AccountType.USD)){
                amount = amount + 1;
            }
        }
        this.accountRepository.transferMoney(senderAccount.getBalance()- amount,senderAccountId, date);
        this.accountRepository.transferMoney(receiverAccount.getBalance() + transferAmount, receiverAccountId, date);
        return true;
    }


    @Override
    public Account findById(int id){
        Account account = this.accountRepository.getAccount(id);
        return account;
    }

	@Override
	public List<Account> getAllAccounts() {
		User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = (User) this.userRepository.findByUsername(authUser.getUsername());
		List<Account> accounts = (List<Account>) this.accountRepository.findByUserId(authUser.getId());
        return accounts;

	}
	
	@Override
	public Account accountDetail(int id) {
		User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user=(User) this.userRepository.findByUsername(authUser.getUsername());
		Account account=this.accountRepository.findById(id);
		if(account != null && account.isDeleted()==false && user.getId()==account.getUser_id()) {
			return account;
		}
		return null;
	}
    
    
}
