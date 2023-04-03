package com.ApiECommerce.apiec.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ApiECommerce.apiec.DAO.AuthDao;
import com.ApiECommerce.apiec.DAO.IUserDao;
import com.ApiECommerce.apiec.Model.AuthenticationResponse;
import com.ApiECommerce.apiec.Model.LoginDTO;
import com.ApiECommerce.apiec.Model.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	
	@Autowired
	private AuthDao authService;
	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(
		@RequestBody  LoginDTO loginDTO){
		
		return ResponseEntity.ok(authService.authenticate(loginDTO));
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> reg(
			@RequestBody  User user){
			
		return ResponseEntity.ok(authService.register(user));
		
	}
}
