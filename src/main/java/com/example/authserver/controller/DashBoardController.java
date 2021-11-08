package com.example.authserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.authserver.dao.UserRepository;

import io.swagger.annotations.ApiImplicitParam;

@RestController
@CrossOrigin
public class DashBoardController {

	@Autowired
	UserRepository userRepo;
	
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	@GetMapping("/hello")
	public ResponseEntity<?> index() {
		return ResponseEntity.ok("Hello World");
	}
	
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	@GetMapping("/admin")
	public ResponseEntity<?> admin() {
		return ResponseEntity.ok("Hello Admin");
	}
	
}
