package com.example.bankingBackend.controllers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankingBackend.models.Bank;
import com.example.bankingBackend.requests.BankCreateRequest;
import com.example.bankingBackend.responses.BankCreateResponse;
import com.example.bankingBackend.services.IBankService;

@RestController
public class BankController {

    @Autowired
    private IBankService bankService;
    
    
    @PostMapping("/bank")
    public ResponseEntity<?> saveUser(@RequestBody BankCreateRequest request){
        BankCreateResponse bankCreateResponse = new BankCreateResponse();
        if(request.getName() == null){
            bankCreateResponse.setSuccess(false);
            bankCreateResponse.setMessage("Given name Already Used :" + request.getName());
            return new ResponseEntity<>(bankCreateResponse, HttpStatus.CONFLICT);
        }
        Bank bank = this.bankService.create(request.getName());
        if(Objects.nonNull(bank.getId())){
            bankCreateResponse.setSuccess(true);
            bankCreateResponse.setMessage("Created Successfully");
            bankCreateResponse.setBank(bank);
        }
        return ResponseEntity.ok().body(bankCreateResponse);
    }
    
    
    
}
