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

import com.jbk.shopify.model.Supplier;
import com.jbk.shopify.service.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

	@Autowired
	private SupplierService supplierService;

	@PostMapping("/save-supplier")
	public Object saveSupplier(@RequestBody Supplier supplier) {
		boolean isAdded = supplierService.saveSupplier(supplier);

		if (isAdded) {
			return supplier;
		} else {
			return "Already Exists";
		}

	}

	@GetMapping("get-supplier-by-id/{id}")
	public Object getCategoryByID(@PathVariable("id") long id) {
		Supplier supplier = supplierService.getSupplierById(id);
		if (supplier != null) {
			return supplier;
		} else {
			return "Supplier Not Exists With Id = " + id;
		}

	}

	@GetMapping("/get-all-supplier")
	public Object getAllSupplier() {

		List<Supplier> list = supplierService.getAllSupplier();

		if (list.isEmpty()) {
			return "Supplier Not Exists";
		} else {
			return list;
		}

	}

	@DeleteMapping("delete-by-id/{id}")
	public String deleteById(@PathVariable("id") long id) {
		boolean isDeleted = supplierService.deleteSupplierById(id);
		if (isDeleted) {
			return "Deleted !!";
		} else {
			return "Supplier Not Found To Deleted With Id = " + id;
		}
	}

	@PutMapping("update-supplier")
	public String updateSupplier(@RequestBody Supplier supplier) {
		boolean isUpdated = supplierService.updateSupplier(supplier);
		if (isUpdated) {
			return "Updated !!";
		} else {
			return "Supplier Not Fount To Update !!";
		}
	}

}
