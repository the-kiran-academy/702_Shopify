package com.jbk.shopify.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.jbk.shopify.model.FinalProduct;
import com.jbk.shopify.model.Product;

public interface ProductService {

	public Object saveProduct(Product product);

	public Product getProductById(long productId);
	
	public Product getProductByName(String productName);

	public List<Product> getAllProduct();

	public boolean deleteProductById(long productId);

	public boolean updateProduct(Product product);

	public List<Product> getAllProductByOrder_Field(String orderType, String name);

	public List<Product> getMaxPriceProducts();

	public List<Product> getProductsContainsWith(String subStringOfProduct);

	public int getTotalCountOfProducts();

	public List<Product> getProductsMoreThanGivenPrice(double price);

	public FinalProduct getFinalProductById(long id);
	
	public Object uploadSheet(MultipartFile file);

}
