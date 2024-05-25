package com.lendoassignment.lendo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lendoassignment.lendo.model.Users;
import com.lendoassignment.lendo.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users user = userRepository.findByEmail(username);
		
		if(user== null)
		{
			throw new UsernameNotFoundException("User not found" +username);
		}
		
		List<GrantedAuthority> authorites = new ArrayList<>();
		
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorites);
	}

}
