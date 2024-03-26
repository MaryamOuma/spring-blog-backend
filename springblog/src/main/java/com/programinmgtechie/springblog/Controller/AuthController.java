package com.programinmgtechie.springblog.Controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programinmgtechie.springblog.dto.RegisterRequest;
import com.programinmgtechie.springblog.model.User;
import com.programinmgtechie.springblog.responses.LoginResponse;
import com.programinmgtechie.springblog.dto.LoginRequest;
import com.programinmgtechie.springblog.service.AuthService;
import com.programinmgtechie.springblog.service.JWTService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	   private final JWTService jwtService;
//The API call should contain the request body which is of type RegisterRequest. We call this kind of classes as a DTO (Data Transfer Object).
	@Autowired
    private AuthService authService;
	  public AuthController(JWTService jwtService, AuthService authenticationService) {
	        this.jwtService = jwtService;
	        this.authService = authenticationService;
	    }
//we will change the reutrn type of the sign up method from void to Responseentity to go back to the client with a reponse its a build in class in springboot 	
	@PostMapping("/signup")
	public ResponseEntity signup(@RequestBody RegisterRequest registerRequest) {
		  authService.signup(registerRequest);
		  return new ResponseEntity(HttpStatus.OK);
		
	}
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
	    try {
	        User authenticatedUser = authService.login(loginRequest);
	        String jwtToken = jwtService.generateToken(authenticatedUser);

	        // Log successful authentication
	        System.out.println("User " + loginRequest.getUsername() + " authenticated successfully"+ jwtToken);

	        LoginResponse loginResponse = new LoginResponse();
	        loginResponse.setToken(jwtToken);
	        loginResponse.setExpiresIn(jwtService.getExpirationTime());

	        return ResponseEntity.ok(loginResponse);
	    } catch (Exception e) {
	        // Log authentication failure
	        System.out.println("Authentication failed for user " + loginRequest.getUsername() + ": " +"password"+ loginRequest.getPassword()+ e.getMessage());
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed for user " + loginRequest.getPassword() + "user " + loginRequest.getUsername() + ": "+ e.getMessage());
	    }
	}

	}


