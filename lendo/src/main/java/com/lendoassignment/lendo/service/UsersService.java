package com.lendoassignment.lendo.service;

import java.util.List;

import com.lendoassignment.lendo.exception.UsersException;
import com.lendoassignment.lendo.model.Users;

public interface UsersService {
	
	public Users findUserfromJwt(String jwt);
	
	public Users findUserById(Long id) throws UsersException;
	
	public List<Users> getAllUsers();

}
