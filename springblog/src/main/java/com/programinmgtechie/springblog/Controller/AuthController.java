package com.programinmgtechie.springblog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programinmgtechie.springblog.dto.RegisterRequest;
import com.programinmgtechie.springblog.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
//The API call should contain the request body which is of type RegisterRequest. We call this kind of classes as a DTO (Data Transfer Object).
	@Autowired
    private AuthService authService;
//we will change the reutrn type of the sign up method from void to Responseentity to go back to the client with a reponse its a build in class in springboot 	
	@PostMapping("/signup")
	public ResponseEntity signup(@RequestBody RegisterRequest registerRequest) {
		  authService.signup(registerRequest);
		  return new ResponseEntity(HttpStatus.OK);
		
	}
}
