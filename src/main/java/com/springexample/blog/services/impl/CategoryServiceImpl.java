package com.springexample.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springexample.blog.entities.Category;
import com.springexample.blog.exceptions.ResourceNotFoundException;
import com.springexample.blog.payloads.CategoryDTO;
import com.springexample.blog.repositories.CategoryRepo;
import com.springexample.blog.repositories.UserRepo;
import com.springexample.blog.services.CategoryService;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		Category cat = this.modelMapper.map(categoryDTO, Category.class);
		Category addedCat = this.categoryRepo.save(cat);
		
		// TODO Auto-generated method stub
		return this.modelMapper.map(addedCat, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		cat.setCategoryTitle(categoryDTO.getCategoryTitle());
		cat.setCategoryDescription(categoryDTO.getCategoryDescription());
		Category updatedCat = this.categoryRepo.save(cat);
		// TODO Auto-generated method stub
		return this.modelMapper.map(updatedCat, CategoryDTO.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "category id", categoryId));
		this.categoryRepo.delete(cat);
		// TODO Auto-generated method stub
		
	}

	@Override
	public CategoryDTO getCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "category id", categoryId));
		// TODO Auto-generated method stub
		return this.modelMapper.map(cat, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getCategories() {
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDTO> catDTOs = categories.stream().map((cat)-> this.modelMapper.map(cat, CategoryDTO.class)).collect(Collectors.toList());
		// TODO Auto-generated method stub
		return catDTOs;
	}

}
