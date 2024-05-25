package com.lendoassignment.lendo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lendoassignment.lendo.model.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

	Users findByEmail(String username);

}
