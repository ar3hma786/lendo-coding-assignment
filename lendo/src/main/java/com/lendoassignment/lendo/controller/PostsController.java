package com.lendoassignment.lendo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lendoassignment.lendo.exception.PostsException;
import com.lendoassignment.lendo.exception.UsersException;
import com.lendoassignment.lendo.model.Posts;
import com.lendoassignment.lendo.model.Users;
import com.lendoassignment.lendo.service.PostsService;
import com.lendoassignment.lendo.service.UsersService;

@RestController
@RequestMapping("/public/v2")
public class PostsController {

    @Autowired
    private PostsService postService;
    
    @Autowired
    private UsersService userService;

    @PostMapping("/posts")
    public ResponseEntity<Posts> createPost(@RequestBody Posts post, @RequestHeader("Authorization") String jwt) throws PostsException, UsersException {
        Users userId = userService.findUserfromJwt(jwt);
        Posts createdPost = postService.createPost(post, userId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long post_Id, @RequestHeader("Authorization") String jwt) throws PostsException, UsersException {
      
    	Users userId = userService.findUserfromJwt(jwt);
        String deletedPostMessage = postService.deletePost(post_Id, userId);
        return new ResponseEntity<>(deletedPostMessage, HttpStatus.OK);
    }

    @GetMapping("/posts/user/{userId}")
    public ResponseEntity<List<Posts>> findPostsByUserId(@PathVariable Long user_Id) throws PostsException, UsersException {
        List<Posts> postsByUserId = postService.findPostByUserId(user_Id);
        return new ResponseEntity<>(postsByUserId, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Posts> findPostById(@PathVariable Long postId) throws PostsException {
        Posts postById = postService.findPostById(postId);
        return new ResponseEntity<>(postById, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Posts>> findAllPosts() {
        List<Posts> allPosts = postService.findAllPosts();
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }
}
