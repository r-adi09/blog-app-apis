package com.springexample.blog.services;

import java.util.List;

import com.springexample.blog.payloads.CategoryDTO;

public interface CategoryService {
	
	//create
	CategoryDTO createCategory(CategoryDTO categoryDTO);
	//update
	CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId);
	//delete
	void deleteCategory(Integer categoryId);
	//get
	CategoryDTO getCategory(Integer categoryId);
	//get all
	List<CategoryDTO> getCategories();

}
