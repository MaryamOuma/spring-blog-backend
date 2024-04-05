package com.programinmgtechie.springblog.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.programinmgtechie.springblog.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	 private final UserRepository userRepository;

	    public SecurityConfig(UserRepository userRepository) {
	        this.userRepository = userRepository;
	    }
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

   /* @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	  http.csrf().disable()
          .authorizeHttpRequests((authorize) ->
              authorize
                  .requestMatchers("/auth/**").permitAll() // permit access to authentication endpoints
                  .anyRequest().authenticated() // require authentication for any other request
          )
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    	  return http.build();
  }*/
    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
//AuthenticationManagerBuilder is a class cretaed to manage the login fuctionality in spring :we will use jdbc that means database credentials to manage the authentification we take the credentials by username and we pass them to spring
//to do that we will user the userdetailsservice interface that loads the user data so we can pass it to spring 
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}

