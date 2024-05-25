package com.lendoassignment.lendo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lendoassignment.lendo.model.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Long>{

}
