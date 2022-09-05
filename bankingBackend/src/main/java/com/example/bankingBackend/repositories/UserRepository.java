package com.example.bankingBackend.repositories;

import java.util.List;

import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface  UserRepository {
	

	public User findByUsername(String username);
	public User findById(int id);
	public int createUser(com.example.bankingBackend.models.User user);
	public int update(int enabled, int id);
	public List<User> getAllUsers();
	public void enabledUser(com.example.bankingBackend.models.User user);
	
}
