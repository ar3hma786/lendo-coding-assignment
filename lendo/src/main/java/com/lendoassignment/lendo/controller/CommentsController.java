package com.lendoassignment.lendo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lendoassignment.lendo.exception.CommentsException;
import com.lendoassignment.lendo.exception.PostsException;
import com.lendoassignment.lendo.exception.UsersException;
import com.lendoassignment.lendo.model.Comments;
import com.lendoassignment.lendo.model.Users;
import com.lendoassignment.lendo.service.CommentsService;
import com.lendoassignment.lendo.service.UsersService;

@RestController
@RequestMapping("/public/v2")
public class CommentsController {
	
	private CommentsService commentService;
	private UsersService userService;
	
	public CommentsController(CommentsService commentService, UsersService userService) {
		super();
		this.commentService = commentService;
		this.userService = userService;
	}


	@PostMapping("/comments/post/{postId}")
	public ResponseEntity<Comments> createComment(@RequestBody Comments comment, @PathVariable("postId") Long postId, @RequestHeader("Authorization") String jwt) throws CommentsException, PostsException, UsersException {
	    Users reqUser = userService.findUserfromJwt(jwt);
	    
	    Comments createdComment = commentService.createComment(comment, postId, reqUser.getUser_id());
	    
	    return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
	}
	
	@GetMapping("/comments/{commentId}")
	public ResponseEntity<Comments> findCommentById(@PathVariable Long commentId) throws CommentsException
	{
		Comments comment = commentService.findCommentById(commentId);
        return new ResponseEntity<Comments>(comment, HttpStatus.OK);

	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/comments")
	public ResponseEntity<List<Comments>> getAllComments()
	{
		List<Comments> comments = commentService.getAllComments();
		return new ResponseEntity<List<Comments>>(comments, HttpStatus.OK);
	}

}
