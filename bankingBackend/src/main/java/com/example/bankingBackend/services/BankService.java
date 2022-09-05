package com.example.bankingBackend.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.bankingBackend.models.Bank;
import com.example.bankingBackend.repositories.BankRepository;

public class BankService implements IBankService {
	@Autowired
	private BankRepository bankRepository;

	@Override
	public Bank create(String name) {

		Bank bank = new Bank();
		bank.setName(name);
		int saveNumber = this.bankRepository.createBank(bank);
		if(saveNumber == 1) {
			Bank bank1 = this.bankRepository.findById(bank.getId());
			return bank1;
		}
		return bank;
	}



}
