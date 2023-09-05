package com.jbk.shopify.service;

import java.util.List;

import com.jbk.shopify.model.Category;

public interface CategoryService {
	
	public Boolean saveCategory(Category category);
	public Category getCategoryById(long categoryId);
	public List<Category> getAllCategory();
	public boolean deleteCategoryById(long categoryId);
	public boolean updateCategory(Category category);
	

}
