package com.springexample.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springexample.blog.entities.Category;
import com.springexample.blog.entities.Post;
import com.springexample.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
	//@Query("select p from Post where p.title like :key")
	List<Post> findByTitleContaining(String title);

}
