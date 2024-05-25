package com.lendoassignment.lendo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lendoassignment.lendo.exception.PostsException;
import com.lendoassignment.lendo.exception.UsersException;
import com.lendoassignment.lendo.model.Posts;
import com.lendoassignment.lendo.model.Users;
import com.lendoassignment.lendo.repository.PostsRepository;
import com.lendoassignment.lendo.repository.UserRepository;

@Service
public class PostsServiceImpl implements PostsService {
    
    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Posts createPost(Posts post, Users user) throws PostsException, UsersException {
        Long userId = user.getUser_id();
        Optional<Users> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new UsersException("User not found with id: " + userId);
        }
        post.setUser(userOpt.get());
        return postsRepository.save(post);
    }


    @Override
    public String deletePost(Long post_Id, Users user_Id) throws PostsException, UsersException {
        Optional<Posts> postOpt = postsRepository.findById(post_Id);
        if (!postOpt.isPresent()) {
            throw new PostsException("Post not found with id: " + post_Id);
        }
        Posts post = postOpt.get();
        if (!post.getUser().getUser_id().equals(user_Id)) { 
            throw new UsersException("User not authorized to delete this post");
        }
        postsRepository.deleteById(post_Id);
        return "Post deleted successfully";
    }

    
    @Override
    public Posts findPostById(Long post_Id) throws PostsException {
        return postsRepository.findById(post_Id)
                .orElseThrow(() -> new PostsException("Post not found with id: " + post_Id));
    }

    @Override
    public List<Posts> findAllPosts() {
        return postsRepository.findAll();
    }

	@Override
	public List<Posts> findPostByUserId(Long userId) throws PostsException, UsersException {
	    
	    Optional<Users> userOptional = userRepository.findById(userId);
	    if (!userOptional.isPresent()) {
	        throw new UsersException("User not found with ID: " + userId);
	    }
	    
	    List<Posts> posts = postsRepository.findByUserId(userId);
	    
	    return posts;
	}

}
