package com.jbk.shopify.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jbk.shopify.exceptions.ProductNotExistsException;
import com.jbk.shopify.model.FinalProduct;
import com.jbk.shopify.model.Product;
import com.jbk.shopify.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/save-product")
	public Object saveProduct(@RequestBody @Valid Product product) {

		Object obj = productService.saveProduct(product);
			return obj;
	}

	@GetMapping("get-product-by-id/{id}")
	public ResponseEntity<Object> getProductByID(@PathVariable("id") long id) {
		Product product = productService.getProductById(id);
		if (product != null) {
			return new ResponseEntity<Object>(product, HttpStatus.FOUND);
		} else {
			//throw new ProductNotExistsException("Product Not Exists");
			
			throw new  RuntimeException("Product Not Exists");
		}
	}

	@GetMapping("/get-all-products")
	public ResponseEntity<List<Product>> getAllProduct() {

		List<Product> list = productService.getAllProduct();
		if (!list.isEmpty()) {
			return new ResponseEntity<List<Product>>(list, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/get-finalproduct-by-id/{id}")
	public ResponseEntity<Object> getFinalProductByID(@PathVariable("id") long id) {
		FinalProduct finalProduct = productService.getFinalProductById(id);
		if (finalProduct != null) {
			return new ResponseEntity<Object>(finalProduct, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<Object>("Product Not Exists With Id = " + id, HttpStatus.NO_CONTENT);
		}

	}

}
