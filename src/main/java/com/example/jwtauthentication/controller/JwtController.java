package com.example.jwtauthentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtauthentication.config.CustomUserDetailServiceImpl;
import com.example.jwtauthentication.helper.JwtUtil;
import com.example.jwtauthentication.model.JwtRequest;
import com.example.jwtauthentication.model.JwtResponse;

@RestController
public class JwtController {
	@Autowired
	private CustomUserDetailServiceImpl customUserDetailServiceImpl;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("token")
	public ResponseEntity<?> getToken(@RequestBody JwtRequest jwtRequest) {

		System.out.println(jwtRequest);

		try {

			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("UseName Not Found !!");
		}
		UserDetails userDetails = customUserDetailServiceImpl.loadUserByUsername(jwtRequest.getUsername());

		String token = jwtUtil.generateToken(userDetails);

		JwtResponse jwtResponse = new JwtResponse(token);

		return ResponseEntity.ok(jwtResponse);

		// return null;

	}

}
