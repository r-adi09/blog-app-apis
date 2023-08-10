package com.springexample.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springexample.blog.config.AppConstants;
import com.springexample.blog.entities.Post;
import com.springexample.blog.payloads.ApiResponse;
import com.springexample.blog.payloads.PostDTO;
import com.springexample.blog.payloads.PostResponse;
import com.springexample.blog.services.FileService;
import com.springexample.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;



@RestController
@RequestMapping("/api/")

public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	

	//@Value("${project.image}")
	@Value("${project.image}")
	private String path;
	
	//create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDTO> createPost(
			@RequestBody PostDTO postDTO,
			@PathVariable Integer userId, 
			@PathVariable Integer categoryId)
	{
		PostDTO createdPost = this.postService.createPost(postDTO, userId, categoryId);
				return new ResponseEntity<PostDTO>(createdPost, HttpStatus.CREATED);
		
	}
	
	//get by id
			@GetMapping("/post/{postId}")
			public ResponseEntity<PostDTO> getPostsById(@PathVariable Integer postId){
				PostDTO posts = this.postService.getPostById(postId);
				return new ResponseEntity<PostDTO>(posts, HttpStatus.OK);
			}
	
	//get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable Integer userId){
		List<PostDTO> posts = this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
	}
	
	//get by category
		@GetMapping("/category/{categoryId}/posts")
		public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable Integer categoryId){
			List<PostDTO> posts = this.postService.getPostsByCategory(categoryId);
			return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
		}
		
	//get all posts
		@GetMapping("/posts")
		public ResponseEntity<PostResponse> getAllPosts(
				@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
				@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
				@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
				@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required=false) String sortDir
				
				){
			
			PostResponse postResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
			return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
		}
		
	//delete post
		@DeleteMapping("/posts/{postId}")
		public ApiResponse deletePost(@PathVariable Integer postId) {
			this.postService.deletePost(postId);
			return new ApiResponse("Post is successfully deleted", true);
			
		}
		
	//update post
		@PutMapping("/posts/{postId}")
		public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Integer postId){
			PostDTO updatedPost = this.postService.updatePost(postDTO, postId);
			return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.OK);
		}
		
	//search
		@GetMapping("/posts/search/{keywords}")
		public ResponseEntity<List<PostDTO>> searchPostByTitle(
				@PathVariable("keywords") String keywords
				){
			List<PostDTO> result = this.postService.searchPosts(keywords);
			return new ResponseEntity<List<PostDTO>>(result, HttpStatus.OK);
			
		}
		
		//post image upload
		@PostMapping("/post/image/upload/{postId}")
		public ResponseEntity<PostDTO> uploadPostImage(
				@RequestParam("image") MultipartFile image,
				@PathVariable Integer postId
				) throws IOException{
			PostDTO postDTO= this.postService.getPostById(postId);

			String fileName = this.fileService.uploadImage(path, image);
			postDTO.setImageName(fileName);
			PostDTO updatedPost = this.postService.updatePost(postDTO, postId);
			return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.OK);
			
		}
		
		//method to serve files
		@GetMapping(value = "post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
		public void downloadImage(
				@PathVariable("imageName") String imageName,
				HttpServletResponse response
				) throws IOException {
			InputStream resource = this.fileService.getResource(path, imageName);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(resource, response.getOutputStream());
		}
	
}

