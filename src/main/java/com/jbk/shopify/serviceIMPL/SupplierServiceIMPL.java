package com.jbk.shopify.serviceIMPL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbk.shopify.dao.SupplierDao;
import com.jbk.shopify.model.Supplier;
import com.jbk.shopify.service.SupplierService;

@Service
public class SupplierServiceIMPL implements SupplierService {

	@Autowired
	private SupplierDao supplierDao;

	@Override
	public Boolean saveSupplier(Supplier supplier) {
		return supplierDao.saveSupplier(supplier);
	}

	@Override
	public Supplier getSupplierById(long supplierId) {
		return supplierDao.getSupplierById(supplierId);
	}

	@Override
	public List<Supplier> getAllSupplier() {
		return supplierDao.getAllSupplier();
	}

	@Override
	public boolean deleteSupplierById(long supplierId) {
		return supplierDao.deleteSupplierById(supplierId);
	}

	@Override
	public boolean updateSupplier(Supplier supplier) {
		return supplierDao.updateSupplier(supplier);
	}

}
