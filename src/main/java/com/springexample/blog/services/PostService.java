package com.springexample.blog.services;

import java.util.List;

import com.springexample.blog.entities.Post;
import com.springexample.blog.payloads.PostDTO;
import com.springexample.blog.payloads.PostResponse;

public interface PostService {
	
	//create
	PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);
	
	//update
	PostDTO updatePost(PostDTO postDTO, Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//get all posts
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//get single post
	PostDTO getPostById(Integer postId);
	
	//get all posts by category
	List<PostDTO> getPostsByCategory(Integer categoryId);
	
	//get all posts by user
	List<PostDTO> getPostsByUser(Integer userId);
	
	//Search posts
	List<PostDTO> searchPosts(String keyword);

}
