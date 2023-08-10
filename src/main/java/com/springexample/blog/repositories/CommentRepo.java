package com.springexample.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springexample.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
