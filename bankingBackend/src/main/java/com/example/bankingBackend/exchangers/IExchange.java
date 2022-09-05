package com.example.bankingBackend.exchangers;

import com.example.bankingBackend.enums.AccountType;

public interface IExchange {

    public double exchange(double amount, AccountType base, AccountType to);

}
