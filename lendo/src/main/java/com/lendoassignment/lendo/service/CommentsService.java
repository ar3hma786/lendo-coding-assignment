package com.lendoassignment.lendo.service;

import java.util.List;

import com.lendoassignment.lendo.exception.CommentsException;
import com.lendoassignment.lendo.exception.PostsException;
import com.lendoassignment.lendo.exception.UsersException;
import com.lendoassignment.lendo.model.Comments;

public interface CommentsService {
	
	public Comments createComment(Comments comment, Long postId, Long userId) throws CommentsException, UsersException, PostsException;

	
	public Comments findCommentById(Long commentId) throws CommentsException;


	public List<Comments> getAllComments(); 

}
