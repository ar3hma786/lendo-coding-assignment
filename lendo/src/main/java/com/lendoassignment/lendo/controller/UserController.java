package com.lendoassignment.lendo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lendoassignment.lendo.config.JwtProvider;
import com.lendoassignment.lendo.exception.UsersException;
import com.lendoassignment.lendo.model.Users;
import com.lendoassignment.lendo.repository.UserRepository;
import com.lendoassignment.lendo.request.LoginRequest;
import com.lendoassignment.lendo.response.AuthResponse;
import com.lendoassignment.lendo.service.CustomUserDetailsService;
import com.lendoassignment.lendo.service.UsersService;


@RestController
public class UserController {
	
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private JwtProvider jwtProvider;
	private CustomUserDetailsService customUserDetailsService;
	private UsersService userService;
	
	

	public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider,
			CustomUserDetailsService customUserDetailsService, UsersService userService) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtProvider = jwtProvider;
		this.customUserDetailsService = customUserDetailsService;
		this.userService = userService;
	}

	@PostMapping("/auth/createuser")
	public ResponseEntity<AuthResponse> createUser(@RequestBody Users user) throws UsersException {

		String email = user.getEmail();
	    String password = user.getPassword();
	    String name = user.getName();
	    String gender = user.getGender();

	   
	    Users isEmailExist = userRepository.findByEmail(email);
	    if (isEmailExist != null) {
	        throw new UsersException("Email Is Already Used With Another Account");
	    }

	   
	    Users createdUser = new Users();
	    createdUser.setEmail(email);
	    createdUser.setName(name);
	    createdUser.setPassword(passwordEncoder.encode(password)); 
	    createdUser.setGender(gender);
	    
	    
	    userRepository.save(createdUser);

	    
	    Authentication authentication = new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>());
	    SecurityContextHolder.getContext().setAuthentication(authentication);

	    
	    String token = jwtProvider.generateToken(authentication);

	    
	    AuthResponse authResponse = new AuthResponse();
	    authResponse.setToken(token);
	    authResponse.setMessage("Register Success");

		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);

	}
	
	@PostMapping("/auth/loginuser")
    public ResponseEntity<AuthResponse> loginuser(@RequestBody LoginRequest loginRequest) throws UsersException {
		
		        String email = loginRequest.getEmail();
		        String password =  loginRequest.getPassword();
		        
		        System.out.println(email + " ----- " + password);

				Authentication authentication = authenticate(email, password);
				SecurityContextHolder.getContext().setAuthentication(authentication);

				String token = jwtProvider.generateToken(authentication);
				AuthResponse authResponse = new AuthResponse();

				authResponse.setToken(token);
				authResponse.setMessage("Login Success");
				
				return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);			
				
	}


	private Authentication authenticate(String email, String password) {
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

		System.out.println("sign in userDetails - " + userDetails);

		if (userDetails == null) {
			System.out.println("sign in userDetails - null " + userDetails);
			throw new BadCredentialsException("Invalid username or password");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			System.out.println("sign in userDetails - password not match " + userDetails);
			throw new BadCredentialsException("Invalid username or password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/api/getAll")
	public ResponseEntity<List<Users>> getAllUsers() {
	    List<Users> users = userService.getAllUsers();
	    return new ResponseEntity<>(users, HttpStatus.OK);
	}

	
}
