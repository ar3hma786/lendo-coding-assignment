package com.lendoassignment.lendo.service;


import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lendoassignment.lendo.exception.CommentsException;
import com.lendoassignment.lendo.exception.PostsException;
import com.lendoassignment.lendo.exception.UsersException;
import com.lendoassignment.lendo.model.Comments;
import com.lendoassignment.lendo.model.Posts;
import com.lendoassignment.lendo.model.Users;
import com.lendoassignment.lendo.repository.CommentsRepository;

@Service
public class CommentsServiceImpl implements CommentsService
{

	private CommentsRepository commentRepository;
	private PostsService postService;
	private UsersService userService;


	public CommentsServiceImpl(CommentsRepository commentRepository, PostsService postService,
			UsersService userService) {
		super();
		this.commentRepository = commentRepository;
		this.postService = postService;
		this.userService = userService;
	}


	@Override
	public Comments createComment(Comments comment, Long postId, Long userId)
	        throws CommentsException, UsersException, PostsException {
	    Users findUser = userService.findUserById(userId);
	    Posts findPost = postService.findPostById(postId);
	    
	    comment.setUser(findUser);
	    comment.setPost(findPost);
	    
	    Comments savedComment = commentRepository.save(comment);
	    return savedComment;
	}


	@Override
	public Comments findCommentById(Long commentId) throws CommentsException {
        Optional<Comments> comment = commentRepository.findById(commentId);
		
		if(comment.isEmpty())
		{
			throw new CommentsException("Comment not exist");
		}
		
		return comment.get();
	}




	

}
