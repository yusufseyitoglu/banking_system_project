package com.example.bankingBackend.repositories;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.bankingBackend.models.Bank;

@Mapper
public interface  BankRepository {

	public int createBank(Bank bank);
	public Bank findById(int id);
	public List<Bank> getAllBanks();

}
