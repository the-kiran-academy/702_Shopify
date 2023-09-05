package com.jbk.shopify.serviceIMPL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbk.shopify.dao.CategoryDao;
import com.jbk.shopify.model.Category;
import com.jbk.shopify.service.CategoryService;

@Service
public class CategoryServiceIMPL implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public Boolean saveCategory(Category category) {
		
		return categoryDao.saveCategory(category);
	}

	@Override
	public Category getCategoryById(long categoryId) {
		
		return categoryDao.getCategoryById(categoryId);
	}

	@Override
	public List<Category> getAllCategory() {
		return categoryDao.getAllCategory();
	}

	@Override
	public boolean deleteCategoryById(long categoryId) {
		return categoryDao.deleteCategoryById(categoryId);
	}

	@Override
	public boolean updateCategory(Category category) {
		return categoryDao.updateCategory(category);
	}

}
