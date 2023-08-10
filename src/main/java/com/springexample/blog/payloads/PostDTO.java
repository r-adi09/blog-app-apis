package com.springexample.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.springexample.blog.entities.Category;
import com.springexample.blog.entities.Comment;
import com.springexample.blog.entities.User;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class PostDTO {
	
	private Integer postId;
	
	private String title;
	
	private String content;
	
	private String imageName = "default.png";
	
	private Date addedDate;
	
	private CategoryDTO category;
	
	private UserDTO user;
	
	private Set<CommentDTO> comments = new HashSet<>();
	

}
