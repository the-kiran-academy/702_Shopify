package com.jbk.shopify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jbk.shopify.model.Category;
import com.jbk.shopify.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/save-category")
	public Object saveCategory(@RequestBody Category category) {
		boolean isAdded = categoryService.saveCategory(category);
		
		if(isAdded) {
			return category;
		}else {
			return "Already Exists";
		}
		

	}
	
	@GetMapping("get-category-by-id/{id}")
	public Object getCategoryByID(@PathVariable("id") long id) {
		Category category = categoryService.getCategoryById(id);
		if(category!=null) {
			return category;
		}else {
			return "Category Not Exists With Id = "+id;
		}
		
		
		
	}
	
	@GetMapping("/get-all-category")
	public Object getAllCategory(){
		
		List<Category> list = categoryService.getAllCategory();
		
		if(list.isEmpty()) {
			return "Category Not Exists";
		}else {
			return list;
		}
		
	}
	
	@DeleteMapping("delete-by-id/{id}")
	public String deleteById(@PathVariable("id") long id) {
		boolean isDeleted = categoryService.deleteCategoryById(id);
		if(isDeleted) {
			return "Deleted !!";
		}else {
			return "Not Found To Deleted With Id = "+id;
		}
	}
	
	
	@PutMapping("update-category")
	public String updateCategory(@RequestBody Category category) {
		boolean isUpdated = categoryService.updateCategory(category);
		if(isUpdated) {
			return "Updated !!";
		}else{
			return "Category Not Fount To Update !!";
		}
	}
	
	
}
