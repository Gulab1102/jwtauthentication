package com.example.jwtauthentication.model;

public class JwtResponse {
	
 private String jwtToken;
	
private	String username;

	public JwtResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JwtResponse(String jwtToken, String username) {
		super();
		this.jwtToken = jwtToken;
		this.username = username;
	}

	public String getToken() {
		return jwtToken;
	}

	public void setToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "JwtResponse [token=" + jwtToken + ", username=" + username + "]";
	}

	
}
