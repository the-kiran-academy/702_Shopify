package com.jbk.shopify.service;

import java.util.List;

import com.jbk.shopify.model.Supplier;

public interface SupplierService {
	public Boolean saveSupplier(Supplier  supplier );

	public Supplier getSupplierById(long supplierId);

	public List<Supplier> getAllSupplier();

	public boolean deleteSupplierById(long supplierId);

	public boolean updateSupplier(Supplier supplier);
}
