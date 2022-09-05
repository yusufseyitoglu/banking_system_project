package com.example.bankingBackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankingBackend.models.User;
import com.example.bankingBackend.requests.UserEnableRequest;
import com.example.bankingBackend.responses.UserEnableResponse;
import com.example.bankingBackend.services.UserService;


@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	
	@PatchMapping(path="users/{id}")
	public ResponseEntity<?> enableDisableUser(@PathVariable int id,@RequestBody UserEnableRequest request){
		User user=this.userService.enabled(request.isEnabled(),id);
		if(user!=null) {
			UserEnableResponse resp=new UserEnableResponse();
			resp.setStatus("success");
			if(request.isEnabled()==true) {
				resp.setMessage("User enabled");
			}else {
				resp.setMessage("User disabled");
			}
			return ResponseEntity.ok().body(resp);
		}
		return ResponseEntity.notFound().build();
	}
}
