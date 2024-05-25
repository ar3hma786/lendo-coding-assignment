package com.lendoassignment.lendo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lendoassignment.lendo.config.JwtProvider;
import com.lendoassignment.lendo.exception.UsersException;
import com.lendoassignment.lendo.model.Users;
import com.lendoassignment.lendo.repository.UserRepository;


@Service
public class UsersServiceImpl implements UsersService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Override
	public List<Users> getAllUsers() {
		
		return userRepository.findAll();
	}

	@Override
	public Users findUserfromJwt(String jwt) {
		String email = jwtProvider.getEmailFromJwtToken(jwt);
		
		Users user = userRepository.findByEmail(email);
		return user;
	}

	public Users findUserById(Long id) throws UsersException {
	    Optional<Users> userOptional = userRepository.findById(id);

	    if (userOptional.isPresent()) {
	        return userOptional.get();
	    } else {
	        throw new UsersException("User not found with ID " + id);
	    }
	}


}
