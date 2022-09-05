package com.example.bankingBackend.services;

import com.example.bankingBackend.models.User;

public interface IUserService {
	public User create(String username, String email, String password);
	public User enabled(boolean enabled, int id);
}
