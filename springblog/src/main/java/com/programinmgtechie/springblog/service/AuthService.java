package com.programinmgtechie.springblog.service;


import java.io.DataInput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.programinmgtechie.springblog.dto.LoginRequest;
import com.programinmgtechie.springblog.dto.RegisterRequest;
import com.programinmgtechie.springblog.model.User;
import com.programinmgtechie.springblog.repository.UserRepository;



@Service
public class AuthService {
	//Spring will attempt to inject the appropriate bean into that component at runtime. means @Autowired helps that we inject user repository data into that objectof type User Repository at runtime
	@Autowired
    private UserRepository userRepository;
	//passwordencoder is an interface inside springsecurity
	  @Autowired
	  private PasswordEncoder passwordEncoder;
	private AuthenticationManager authenticationManager;
	  public AuthService(
		        UserRepository userRepository,
		        AuthenticationManager authenticationManager,
		        PasswordEncoder passwordEncoder
		    ) {
		        this.authenticationManager = authenticationManager;
		        this.userRepository = userRepository;
		        this.passwordEncoder = passwordEncoder;
		    }
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));

        userRepository.save(user);
    }
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
    
    public User login(LoginRequest loginRequest) {
        // Perform authentication without any account status checks
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        // Retrieve and return the authenticated user from the repository
        return userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow();
    }

}
