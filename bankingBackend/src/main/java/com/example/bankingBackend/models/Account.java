package com.example.bankingBackend.models;

import java.security.Timestamp;
import java.util.Date;

import org.apache.ibatis.type.Alias;
import com.example.bankingBackend.enums.AccountType;
import lombok.Data;

@Alias("Account")
@Data
public class Account {

    private int id;
    private int user_id;
	private int bank_id;
    private long number;
    private double balance;
    private AccountType type;
    private boolean deleted;
    private User user;
    private Bank bank;
	private Date creation_date;
	private Date last_update_date;
}
