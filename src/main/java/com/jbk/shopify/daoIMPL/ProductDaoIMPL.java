package com.jbk.shopify.daoIMPL;

import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbk.shopify.dao.ProductDao;
import com.jbk.shopify.entity.CategoryEntity;
import com.jbk.shopify.entity.ProductEntity;
import com.jbk.shopify.model.Category;
import com.jbk.shopify.model.Product;
import com.jbk.shopify.utility.EntityToModel;
import com.jbk.shopify.utility.ModelToEntity;

@Repository
public class ProductDaoIMPL implements ProductDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ModelToEntity modelToEntity;

	@Autowired
	private EntityToModel entityToModel;

	@Override
	public int saveProduct(Product product) {

		try (Session session = sessionFactory.openSession();) {

			ProductEntity productEntity = modelToEntity.convertToEntity(product);
			session.save(productEntity);
			session.beginTransaction().commit();
			return 1;

		} catch (Exception e) {
			e.printStackTrace();

		}

		return 0;
	}

	@Override
	public Product getProductById(long productId) {
		Product productModel = null;

		try (Session session = sessionFactory.openSession();){
			ProductEntity productEntity = session.get(ProductEntity.class, productId);
			if (productEntity != null) {
				productModel = entityToModel.convertToModel(productEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return productModel;
	}

	@Override
	public List<Product> getAllProduct() {
		
		List<Product> list=null;
		try(Session session = sessionFactory.openSession();) {
			Criteria criteria = session.createCriteria(ProductEntity.class);
			 list = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return list;
	}

	@Override
	public boolean deleteProductById(long productId) {
		Session session = sessionFactory.openSession();

		ProductEntity productEntity = session.get(ProductEntity.class, productId);

		if (productEntity != null) {
			session.delete(productEntity);
			session.beginTransaction().commit();
			return true;
		}

		return false;
	}

	@Override
	public boolean updateProduct(Product product) {
		Session session = sessionFactory.openSession();

		Product dbProduct = getProductById(product.getProductId());

		if (dbProduct != null) {

			ProductEntity productEntity = modelToEntity.convertToEntity(product);
			session.update(productEntity);
			session.beginTransaction().commit();
			return true;
		}

		return false;
	}

	@Override
	public List<Product> getAllProductByOrder_Field(String orderType, String name) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(ProductEntity.class);
		if ("desc".equalsIgnoreCase(orderType)) {
			criteria.addOrder(Order.desc(name));
		} else {
			criteria.addOrder(Order.asc(name));
		}

		return criteria.list();
	}

	public long maxPrice() {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(ProductEntity.class);
		long maxPrice = (long) criteria.setProjection(Projections.max("productPrice")).list().get(0);

		return maxPrice;

	}

	@Override
	public List<Product> getMaxPriceProducts() {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(ProductEntity.class);
		criteria.add(Restrictions.eq("productPrice", maxPrice()));

		return criteria.list();
	}

	@Override
	public List<Product> getProductsContainsWith(String subStringOfProduct) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(ProductEntity.class);
		criteria.add(Restrictions.like("productName", "%" + subStringOfProduct + "%"));
		return criteria.list();
	}

	@Override
	public int getTotalCountOfProducts() {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(ProductEntity.class);
		int count = (int) criteria.setProjection(Projections.rowCount()).list().get(0);

		return count;
	}

	@Override
	public List<Product> getProductsMoreThanGivenPrice(double price) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(ProductEntity.class);
		criteria.add(Restrictions.gt("productPrice", price));

		return criteria.list();
	}

	@Override
	public Product getProductByName(String productName) {
		Product productModel = null;
		try (Session session = sessionFactory.openSession();) {

			Criteria criteria = session.createCriteria(ProductEntity.class);
			criteria.add(Restrictions.eq("productName", productName));
			ProductEntity productEntity = (ProductEntity) criteria.uniqueResult();
			if (productEntity != null) {
				productModel = entityToModel.convertToModel(productEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productModel;
	}

}
