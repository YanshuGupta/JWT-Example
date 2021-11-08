package com.example.authserver.jwt;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	
	@Email
	private String email;
	@Size(min=6)
	private String password;
	
	//need default constructor for JSON Parsing
	public JwtRequest()
	{
		
	}

	public JwtRequest(String username, String password) {
		this.setEmail(username);
		this.setPassword(password);
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}