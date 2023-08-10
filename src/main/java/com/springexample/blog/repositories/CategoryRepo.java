package com.springexample.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springexample.blog.entities.Category;


public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
