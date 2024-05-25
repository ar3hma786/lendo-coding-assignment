package com.lendoassignment.lendo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lendoassignment.lendo.model.Posts;

public interface PostsRepository extends JpaRepository<Posts, Long>{

    @Query("SELECT p FROM Posts p WHERE p.user.id = :userId")
    List<Posts> findByUserId(Long userId);
  
}
