package com.example.bankingBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.bankingBackend.models.User;
import com.example.bankingBackend.repositories.UserRepository;

public class UserService implements IUserService{
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User create(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(false);
        user.setAuthorities("CREATE_ACCOUNT");
        this.userRepository.createUser(user);
		return (User) this.userRepository.findByUsername(username);
	}
	
	
	@Override
	public User enabled(boolean enabled, int id) {
		
		User user = (User) this.userRepository.findById(id);
		
		if(user!=null) {
			user.setEnabled(enabled);
			this.userRepository.enabledUser(user);
			return user;
		}
		return null;
	}

}
