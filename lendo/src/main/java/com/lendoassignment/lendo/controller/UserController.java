package com.lendoassignment.lendo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lendoassignment.lendo.model.Users;
import com.lendoassignment.lendo.service.UsersService;


@RestController
@RequestMapping("/public/v2")
public class UserController {
	
	
	@Autowired
	private UsersService userService;
	

	@PreAuthorize("isAuthenticated()")
	@GetMapping("users")
	public ResponseEntity<List<Users>> getAllUsers() {
	    List<Users> users = userService.getAllUsers();
	    return new ResponseEntity<>(users, HttpStatus.OK);
	}

	
}
