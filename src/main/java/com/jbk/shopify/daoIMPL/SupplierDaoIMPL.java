package com.jbk.shopify.daoIMPL;

import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbk.shopify.dao.SupplierDao;
import com.jbk.shopify.entity.CategoryEntity;
import com.jbk.shopify.entity.SupplierEntity;
import com.jbk.shopify.model.Category;
import com.jbk.shopify.model.Supplier;
import com.jbk.shopify.utility.EntityToModel;
import com.jbk.shopify.utility.ModelToEntity;

@Repository
public class SupplierDaoIMPL implements SupplierDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ModelToEntity modelToEntity;

	@Autowired
	private EntityToModel entityToModel;

	@Override
	public Boolean saveSupplier(Supplier supplier) {
		Session session = null;
		try {
			SupplierEntity supplierEntity = modelToEntity.convertToEntity(supplier);
			session = sessionFactory.openSession();
			session.save(supplierEntity);
			session.beginTransaction().commit();
		} catch (PersistenceException e) {
			e.printStackTrace();
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
	public Supplier getSupplierById(long supplierId) {
		Supplier supplierModel = null;
		try (Session session = sessionFactory.openSession();) {
			SupplierEntity supplierEntity = session.get(SupplierEntity.class, supplierId);
			if (supplierEntity != null) {
				supplierModel = entityToModel.convertToModel(supplierEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return supplierModel;
	}

	@Override
	public List<Supplier> getAllSupplier() {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(SupplierEntity.class);
		List<Supplier> list = criteria.list();
		return list;
	}

	@Override
	public boolean deleteSupplierById(long supplierId) {
		Session session = sessionFactory.openSession();

		SupplierEntity supplierEntity = session.get(SupplierEntity.class, supplierId);

		if (supplierEntity != null) {
			session.delete(supplierEntity);
			session.beginTransaction().commit();
			return true;
		}

		return false;
	}

	@Override
	public boolean updateSupplier(Supplier supplier) {
		Session session = sessionFactory.openSession();

		Supplier dbSuppliery = getSupplierById(supplier.getSupplierId());

		if (dbSuppliery != null) {

			SupplierEntity supplierEntity = modelToEntity.convertToEntity(supplier);
			session.update(supplierEntity);
			session.beginTransaction().commit();
			return true;
		}

		return false;
	}

}
