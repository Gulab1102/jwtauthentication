package com.example.jwtauthentication.config;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailServiceImpl implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
	   if(username.equals("gk96025@gmail.com"))
		   return new User("gk96025@gmail.com", "Gulab@1102", new ArrayList<>());
	   
	   throw new UsernameNotFoundException("User Not found");
	}

}
