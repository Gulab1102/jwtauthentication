package com.example.jwtauthentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.jwtauthentication.model.User;
import com.example.jwtauthentication.repo.UserRepo;

public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		 User user=userRepo.findByEmail(username);
		 
		 UserDetails userDetails=new CustomUserDetail(user);
		 
		return userDetails;
	}

}
