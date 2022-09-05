package com.example.bankingBackend.controllers;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import com.example.bankingBackend.models.Account;
import com.example.bankingBackend.requests.AccountBalanceDepositRequest;
import com.example.bankingBackend.requests.AccountBalanceTransferRequest;
import com.example.bankingBackend.requests.AccountCreateRequest;
import com.example.bankingBackend.responses.AccountCreateSuccessResponse;
import com.example.bankingBackend.responses.AccountDeleteSuccessResponse;
import com.example.bankingBackend.responses.AccountDepositSuccessResponse;
import com.example.bankingBackend.responses.AccountInvalidTypeResponse;
import com.example.bankingBackend.responses.AccountTransferSuccessResponse;
import com.example.bankingBackend.services.AccountService;


@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private KafkaTemplate<String,String> producer;
    
    @PostMapping("/account")
    public ResponseEntity<?> createAccount(@RequestBody AccountCreateRequest request) {
		Account account = this.accountService.create(request.getBank_id(), request.getType());
		if (account != null) {
			AccountCreateSuccessResponse resp = new AccountCreateSuccessResponse();
			resp.setSuccess(true);
			resp.setMessage("Account Created");
			resp.setAccount(account);
			return ResponseEntity.ok().body(resp);
		} else {
			AccountInvalidTypeResponse resp = new AccountInvalidTypeResponse();
			resp.setSuccess(false);
			resp.setMessage("Invalid Account Type : " + request.getType());
			return ResponseEntity.badRequest().body(resp);
		}
	}
    
    @GetMapping(path = "/accounts/{id}")
	public ResponseEntity<?> accountDetail(@PathVariable int id, WebRequest request) throws IOException {
		Account detail = this.accountService.accountDetail(id);
		if (request.checkNotModified(detail.getLast_update_date().getTime())) {
			return null;
		}
		return ResponseEntity.ok().lastModified(detail.getLast_update_date().getTime()).body(detail);
	}
    
	@DeleteMapping("/accounts/{id}")
	public ResponseEntity<?> deleteAccount(@PathVariable int id) {
		boolean isDeleted = this.accountService.remove(id);
		if (isDeleted == true) {
			AccountDeleteSuccessResponse resp = new AccountDeleteSuccessResponse();
			resp.setSuccess(true);
			resp.setMessage("Deleted Successfully");
			return ResponseEntity.ok().body(resp);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	@PatchMapping(path = "/accounts/{id}")
	public ResponseEntity<?> deposit(@PathVariable int id, @RequestBody AccountBalanceDepositRequest request) throws AccessDeniedException {
		Account account = this.accountService.addAmount(id, request.getAmount());
		AccountDepositSuccessResponse resp = new AccountDepositSuccessResponse();
		resp.setSuccess(true);
		resp.setMessage("Deposit Successfully");
		resp.setBalance(account.getBalance());
		String message = account.getNumber() + ", " + request.getAmount() + " " + account.getType() + " deposited";
		producer.send("logs", message);
		return ResponseEntity.ok().body(resp);
	}
	

    @PostMapping("/account/{senderAccountId}")
    public ResponseEntity<?> transfer(@PathVariable int senderAccountId,@RequestBody AccountBalanceTransferRequest request){
    	Account senderAccount = this.accountService.findById(senderAccountId);
        Account receiverAccount = this.accountService.findById(request.getReceiverAccountId());
    	String info = senderAccount.getNumber() 
    			+ " transfer amount:" 
    			+ request.getAmount()+ " " 
    			+ senderAccount.getType() 
    			+ ",transferred_account:" 
    			+ receiverAccount.getNumber();

    	
    	AccountTransferSuccessResponse resp = new AccountTransferSuccessResponse();
    	resp.setMessage("Transferred Succesfully.");
    	producer.send("logs",info);
 	
    	return ResponseEntity.ok().body(resp); 	
    }


    @GetMapping("/accounts")
    public ResponseEntity<?> getAllAccount() {
		List<Account> account = this.accountService.getAllAccounts();
		if(account != null) {
			return ResponseEntity.ok().body(account);
		}
		return ResponseEntity.notFound().build();
	}
	
		
	
	
	
	
}
