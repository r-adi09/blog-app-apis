package com.springexample.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springexample.blog.payloads.ApiResponse;
import com.springexample.blog.payloads.CategoryDTO;
import com.springexample.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
		CategoryDTO createCategory = this.categoryService.createCategory(categoryDTO);
		return new ResponseEntity<CategoryDTO>(createCategory, HttpStatus.CREATED);
		
	}
	//update
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Integer catId){
		CategoryDTO updatedCategory = this.categoryService.updateCategory(categoryDTO, catId);
		return new ResponseEntity<CategoryDTO>(updatedCategory, HttpStatus.OK);
		
	}
	//delete
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId){
		this.categoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted successfully", true), HttpStatus.OK);
		
	}
	//get
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer catId){
		CategoryDTO categoryDTO = this.categoryService.getCategory(catId);
		return new ResponseEntity<CategoryDTO>(categoryDTO, HttpStatus.OK);
		
	}
	//get all
	@GetMapping("/")
	public ResponseEntity<List<CategoryDTO>> getCategories(){
		List<CategoryDTO> categories = this.categoryService.getCategories();
		return ResponseEntity.ok(categories);
		
	}

}
