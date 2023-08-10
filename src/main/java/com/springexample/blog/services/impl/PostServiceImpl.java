package com.springexample.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springexample.blog.entities.Category;
import com.springexample.blog.entities.Post;
import com.springexample.blog.entities.User;
import com.springexample.blog.exceptions.ResourceNotFoundException;
import com.springexample.blog.payloads.PostDTO;
import com.springexample.blog.payloads.PostResponse;
import com.springexample.blog.repositories.CategoryRepo;
import com.springexample.blog.repositories.PostRepo;
import com.springexample.blog.repositories.UserRepo;
import com.springexample.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
		
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User id", userId));
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "category id", categoryId));
		Post post = this.modelMapper.map(postDTO, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost = this.postRepo.save(post);		
		return this.modelMapper.map(newPost, PostDTO.class);
		
		
		
		// TODO Auto-generated method stub
	}

	@Override
	public PostDTO updatePost(PostDTO postDTO, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","post id", postId));
		post.setTitle(postDTO.getTitle());
		post.setContent(postDTO.getContent());
		post.setImageName(postDTO.getImageName());
		Post updatedPost = this.postRepo.save(post);
		// TODO Auto-generated method stub
		return this.modelMapper.map(updatedPost, PostDTO.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));
		this.postRepo.delete(post);
		// TODO Auto-generated method stub
		
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("asc"))? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
	
		Pageable p = PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> allPosts = pagePost.getContent();
		List<PostDTO>postdto = allPosts.stream().map((post)->this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		// TODO Auto-generated method stub
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postdto);
		postResponse.setPageNUmber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDTO getPostById(Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found with the provided ID ", "post id", postId));
		return this.modelMapper.map(post, PostDTO.class);
		// TODO Auto-generated method stub
	}

	@Override
	public List<PostDTO> getPostsByCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category", "category id", categoryId));
		List<Post>posts = this.postRepo.findByCategory(cat);
		List<PostDTO> postdtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		// TODO Auto-generated method stub
		return postdtos;
	}

	@Override
	public List<PostDTO> getPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "user id", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		System.out.println(posts);
		List<PostDTO> postdtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		// TODO Auto-generated method stub
		return postdtos;
	}

	@Override
	public List<PostDTO> searchPosts(String keyword) {
		List<Post>posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDTO> postsDTO = posts.stream().map((post)->this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		// TODO Auto-generated method stub
		return postsDTO;
	}

}
