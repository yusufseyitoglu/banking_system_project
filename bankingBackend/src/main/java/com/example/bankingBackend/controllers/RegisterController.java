package com.example.bankingBackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.bankingBackend.models.User;
import com.example.bankingBackend.requests.RegisterRequest;
import com.example.bankingBackend.responses.RegisterErrorResponse;
import com.example.bankingBackend.responses.RegisterSuccessResponse;
import com.example.bankingBackend.services.UserService;

public class RegisterController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request){
		
		User user=this.userService.create(request.getUsername(), request.getEmail(), request.getPassword());
		if(user != null) {
			RegisterSuccessResponse resp= new RegisterSuccessResponse();
			resp.setSuccess(true);
			resp.setMessage("Created Successfully");
			resp.setUser(user);
			return ResponseEntity.ok().body(resp);
		}
		RegisterErrorResponse resp= new RegisterErrorResponse();
		resp.setSuccess(false);
		resp.setMessage("Given username or email already Used : "+ request.getUsername()+" or "+request.getEmail());
		return ResponseEntity.unprocessableEntity().body(resp);
	}

}
