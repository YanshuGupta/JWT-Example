package com.example.authserver.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.authserver.dao.UserRepository;
import com.example.authserver.jwt.JwtRequest;
import com.example.authserver.jwt.JwtResponse;
import com.example.authserver.jwt.JwtTokenUtil;
import com.example.authserver.model.User;
import com.example.authserver.service.JWTUserDetailsService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JWTUserDetailsService userDetailsService;

	@Autowired
	private UserRepository userRepo;

	@PostMapping(value = "/authenticate")
	@ApiOperation(value = "Create JWT Token", response = JwtResponse.class)
	@ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"), 
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody @Valid JwtRequest authenticationRequest)
			throws Exception {
		createAdminIfNotExists();

		try {
			authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
			
			final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
			
			final String token = jwtTokenUtil.generateToken(userDetails);
			
			return ResponseEntity.ok(new JwtResponse(token));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JwtResponse(""));
		}
	}

	private void createAdminIfNotExists() {
		if(userRepo.findById("adminuser").isEmpty()) {
			Set<String> roles = new HashSet<>();
			roles.add("ROLE_ADMIN");
			userRepo.save(new User("adminuser@deloitte.com", new BCryptPasswordEncoder().encode("password"), "Admin User", "Devlopment",
					roles));
		}
	}

	@PostMapping("/signup")
	@ApiOperation(value = "signup user", response = User.class)
	public ResponseEntity<User> addUser(@RequestBody @Valid User user) {
		try {
			Optional<User> obj = userRepo.findById(user.getEmail());
			if (obj.isEmpty()) {
				user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
				return ResponseEntity.ok(userRepo.save(user));
			} else {
				return new ResponseEntity<User>(user, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			return ResponseEntity.ok(userRepo.save(user));
		}
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}