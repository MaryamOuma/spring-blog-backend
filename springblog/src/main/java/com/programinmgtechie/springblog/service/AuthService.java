package com.programinmgtechie.springblog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
