package com.springexample.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springexample.blog.entities.Comment;
import com.springexample.blog.entities.Post;
import com.springexample.blog.exceptions.ResourceNotFoundException;
import com.springexample.blog.payloads.CommentDTO;
import com.springexample.blog.repositories.CommentRepo;
import com.springexample.blog.repositories.PostRepo;
import com.springexample.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "post id", postId));
		Comment comment = this.modelMapper.map(commentDTO, Comment.class);
		comment.setPost(post);
		Comment saved = this.commentRepo.save(comment);
		// TODO Auto-generated method stub
		return this.modelMapper.map(saved, CommentDTO.class);
	}

	@Override
	public void deleteComment(Integer comment) {
		
		Comment com = this.commentRepo.findById(comment).orElseThrow(()-> new ResourceNotFoundException("comment", "comment id", comment));
		// TODO Auto-generated method stub
		this.commentRepo.delete(com);

	}

}
