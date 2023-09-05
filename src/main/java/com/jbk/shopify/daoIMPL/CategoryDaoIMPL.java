package com.jbk.shopify.daoIMPL;

import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbk.shopify.dao.CategoryDao;
import com.jbk.shopify.entity.CategoryEntity;
import com.jbk.shopify.model.Category;
import com.jbk.shopify.utility.EntityToModel;
import com.jbk.shopify.utility.ModelToEntity;

@Repository
public class CategoryDaoIMPL implements CategoryDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ModelToEntity modelToEntity;

	@Autowired
	private EntityToModel entityToModel;

	@Override
	public Boolean saveCategory(Category category) {
		Session session = null;
		try {
			CategoryEntity categoryEntity = modelToEntity.convertToEntity(category);
			session = sessionFactory.openSession();
			session.save(categoryEntity);
			session.beginTransaction().commit();
		} catch (PersistenceException e) {
			System.err.println("Already Exists !!");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return true;
	}

	@Override
	public Category getCategoryById(long categoryId) {
		Category categoryModel = null;
		Session session = sessionFactory.openSession();
		CategoryEntity categoryEntity = session.get(CategoryEntity.class, categoryId);
		if (categoryEntity != null) {
			categoryModel = entityToModel.convertToModel(categoryEntity);
		}

		return categoryModel;

	}

	@Override
	public List<Category> getAllCategory() {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(CategoryEntity.class);
		List<Category> list = criteria.list();

//		List<Category> cl=new ArrayList<Category>();
//		
//		for (CategoryEntity categoryEntity : list) {
//			cl.add(entityToModel.convertToModel(categoryEntity));
//		}

		return list;
	}

	@Override
	public boolean deleteCategoryById(long categoryId) {
		Session session = sessionFactory.openSession();

		CategoryEntity categoryEntity = session.get(CategoryEntity.class, categoryId);

		if (categoryEntity != null) {
			session.delete(categoryEntity);
			session.beginTransaction().commit();
			return true;
		}

		return false;
	}

	@Override
	public boolean updateCategory(Category category) {
		Session session = sessionFactory.openSession();

		Category dbCategory = getCategoryById(category.getCategoryId());

		if (dbCategory != null) {

			CategoryEntity categoryEntity = modelToEntity.convertToEntity(category);
			session.update(categoryEntity);
			session.beginTransaction().commit();
			return true;
		}

		return false;
	}

}
