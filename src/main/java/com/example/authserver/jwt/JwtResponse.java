package com.example.authserver.jwt;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private String access_token;
	private String token_type;
	private Set<? extends GrantedAuthority> scope;

	public JwtResponse(String access_token, String token_type, Collection<? extends GrantedAuthority> scope) {
		this.access_token = access_token;
		this.token_type = token_type;
		this.scope = (Set) scope;
	}

	public JwtResponse() {
		super();
	}
	
	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public Set getScope() {
		return scope;
	}

	public void setScope(Set scope) {
		this.scope = scope;
	}

	public String getAccess_token() {
		return access_token;
	}

	
}