package com.jbk.shopify.dao;

import java.util.List;

import com.jbk.shopify.entity.CategoryEntity;
import com.jbk.shopify.model.Category;

public interface CategoryDao {

	public Boolean saveCategory(Category category);
	public Category getCategoryById(long categoryId);
	public List<Category> getAllCategory();
	public boolean deleteCategoryById(long categoryId);
	public boolean updateCategory(Category category);
}
