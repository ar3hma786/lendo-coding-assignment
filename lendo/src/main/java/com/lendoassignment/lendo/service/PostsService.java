package com.lendoassignment.lendo.service;

import java.util.List;

import com.lendoassignment.lendo.exception.PostsException;
import com.lendoassignment.lendo.exception.UsersException;
import com.lendoassignment.lendo.model.Posts;
import com.lendoassignment.lendo.model.Users;

public interface PostsService {
	
	public Posts createPost(Posts post, Users userId) throws PostsException, UsersException;
	
    public String deletePost(Long post_Id, Users userId) throws PostsException, UsersException;
    
    public List<Posts> findPostByUserId(Long user_Id) throws PostsException, UsersException;
    
    public List<Posts> findAllPosts();

	public Posts findPostById(Long post_Id) throws PostsException;
	
	

}
