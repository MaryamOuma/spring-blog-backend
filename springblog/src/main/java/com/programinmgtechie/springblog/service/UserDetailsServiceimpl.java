package com.programinmgtechie.springblog.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.programinmgtechie.springblog.model.User;
import com.programinmgtechie.springblog.repository.UserRepository;

@Service
public class UserDetailsServiceimpl implements UserDetailsService{
	@Autowired
	UserRepository userRepository;

	@Override
	 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	     User user = userRepository.findByUsername(username)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

	        return new org.springframework.security.core.userdetails.User(
	                user.getUserName(),
	                user.getPassword(),
	                new ArrayList<>()
	        );
	}

}
